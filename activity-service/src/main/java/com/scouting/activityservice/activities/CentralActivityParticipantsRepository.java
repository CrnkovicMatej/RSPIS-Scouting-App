package com.scouting.activityservice.activities;

import com.scouting.activityservice.activities.exception.ActivitySavingException;
import com.scouting.activityservice.activities.exception.ConflictingActivityDefinitionException;
import com.scouting.activityservice.activities.exception.InvalidActivityException;
import com.scouting.activityservice.activities.exception.ParticipantActivitySavingException;
import com.scouting.activityservice.activities.model.Activity;
import com.scouting.activityservice.activities.model.ActivityParticipants;
import com.scouting.activityservice.activities.storage.ActivityEntity;
import com.scouting.activityservice.activities.storage.ActivityParticipantsEntity;
import com.scouting.activityservice.activities.storage.ActivityParticipantsRepository;
import com.scouting.activityservice.activities.utils.ActivityPageQuery;
import com.scouting.activityservice.activities.utils.ApiKeyReader;
import com.scouting.activityservice.activities.utils.Page;
import com.scouting.activityservice.activities.utils.UserIdRetrieverService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component

public class CentralActivityParticipantsRepository {

    private final ActivityParticipantsRepository activityParticipantsRepository;
    private final UserIdRetrieverService userIdRetrieverService;

    public CentralActivityParticipantsRepository(
            ActivityParticipantsRepository activityParticipantsRepository,
            UserIdRetrieverService userIdRetrieverService
    ) {
        this.activityParticipantsRepository = activityParticipantsRepository;
        this.userIdRetrieverService = userIdRetrieverService;
    }

    public ActivityParticipants tryToCreateARecord(Activity activity, String api_key){
        var apiKeyReader = new ApiKeyReader(api_key);
        if(apiKeyReader.readApiKey().isPresent()) {
            var user_id = apiKeyReader.readApiKey().get();
            var user_id_long = createEntry(user_id);
            return createRecord(new ActivityParticipants( Long.valueOf(activity.id()), user_id_long));
        }
        else
        {
            throw ParticipantActivitySavingException.becauseUserIdIsWrong();
        }
    }

    private Long createEntry(  String authorizationHeader) {
        return userIdRetrieverService.getUserIdByApiKey_s(authorizationHeader);
    }
    public ActivityParticipants createRecord(ActivityParticipants activityParticipants) {
        var activityEntity = new ActivityParticipantsEntity(
                activityParticipants.activity_id(),
                activityParticipants.user_id()
        );
        return save(activityEntity);
    }

    private ActivityParticipants save(ActivityParticipantsEntity activityEntity) {
        try {
            return ActivityParticipants.fromEntity(activityParticipantsRepository.save(activityEntity));
        } catch (ConstraintViolationException constraintViolationException) {
            throw ConflictingActivityDefinitionException.becauseOfRepositoryConstraintViolation();
        } catch (Exception exception) {
            throw ActivitySavingException.becauseOfExceptionDuringSaving(exception);
        }
    }

    public Page<ActivityParticipants> findAllRecords(ActivityPageQuery activityPageQuery) {
        org.springframework.data.domain.Page<ActivityParticipantsEntity> resultPage;
        var pageRequest = PageRequest.of(activityPageQuery.page(), activityPageQuery.size(), Sort.by("id"));

        resultPage = activityParticipantsRepository.findAll(pageRequest);

        return new Page<>(
                resultPage.get().map(ActivityParticipants::fromEntity).toList(),
                activityPageQuery.page(),
                activityPageQuery.size(),
                resultPage.getTotalPages(),
                resultPage.getTotalElements()
        );
    }
}
