package com.scouting.activityservice.activities;

import com.scouting.activityservice.activities.exception.*;
import com.scouting.activityservice.activities.model.Activity;
import com.scouting.activityservice.activities.storage.ActivityEntity;
import com.scouting.activityservice.activities.storage.ActivityEntityRepository;
import com.scouting.activityservice.activities.utils.ActivityPageQuery;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;
import com.scouting.activityservice.activities.utils.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Component
public class CentralActivitiesRepository {
    private final ActivityEntityRepository activityEntityRepository;

    public CentralActivitiesRepository(ActivityEntityRepository activityEntityRepository) {
        this.activityEntityRepository = activityEntityRepository;
    }

    public Activity getActivity(Integer activityID) {
        return activityEntityRepository.findById(activityID)
                .map(Activity::fromEntity)
                .orElseThrow(() -> ActivityNotFoundException.becauseTheActivityDoesNotExist(activityID));
    }

    public Page<Activity> findAllActivities(ActivityPageQuery activityPageQuery) {
        org.springframework.data.domain.Page<ActivityEntity> resultPage;
        var pageRequest = PageRequest.of(activityPageQuery.page(), activityPageQuery.size(), Sort.by("id"));

        if (activityPageQuery.type().isPresent() ) {
            resultPage = activityEntityRepository.findPageByType(
                    activityPageQuery.type().get(),
                    pageRequest
            );
        }
        else {
            resultPage = activityEntityRepository.findAll(pageRequest);
        }


        return new Page<>(
                resultPage.get().map(Activity::fromEntity).toList(),
                activityPageQuery.page(),
                activityPageQuery.size(),
                resultPage.getTotalPages(),
                resultPage.getTotalElements()
        );
    }

    public Activity createActivity(Activity activity) {
        if (activity.id() != null) {
            throw InvalidActivityException.becauseTheIdIsProvided();
        }

        var libraryEntity = new ActivityEntity(
                null,
                activity.name(),
                activity.startDate(),
                activity.endDate(),
                activity.price(),
                activity.type(),
                activity.description()
        );

        return save(libraryEntity);
    }

    private Activity save(ActivityEntity activityEntity) {
        try {
            return Activity.fromEntity(activityEntityRepository.save(activityEntity));
        } catch (ConstraintViolationException constraintViolationException) {
            throw ConflictingActivityDefinitionException.becauseOfRepositoryConstraintViolation();
        } catch (Exception exception) {
            throw ActivitySavingException.becauseOfExceptionDuringSaving(exception);
        }
    }
}
