package com.scouting.activityservice.api;

import java.util.List;
import java.util.Optional;

import com.scouting.activityservice.activities.CentralActivitiesRepository;
import com.scouting.activityservice.activities.CentralActivityParticipantsRepository;
import com.scouting.activityservice.activities.model.Activity;
import com.scouting.activityservice.activities.model.ActivityParticipants;
import com.scouting.activityservice.activities.storage.ActivityEntity;
import com.scouting.activityservice.activities.utils.ActivityPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.scouting.activityservice.activities.utils.*;
import org.springframework.web.reactive.function.client.WebClient;

@Validated
@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final CentralActivitiesRepository centralActivitiesRepository;
    private final CentralActivityParticipantsRepository centralActivityParticipantsRepository;
    private final WebClient webClient;

    public ActivityController(CentralActivitiesRepository centralActivitiesRepository,
                              CentralActivityParticipantsRepository centralActivityParticipantsRepository,
                              WebClient webClient) {
        this.centralActivitiesRepository = centralActivitiesRepository;
        this.centralActivityParticipantsRepository = centralActivityParticipantsRepository;
        this.webClient = webClient;
    }

    @GetMapping("/getAll")
    public Page<Activity> getAllActivities(
            @RequestParam(required = false) ActivityEntity.ActivityStatus status,
            @RequestParam(required = false) ActivityEntity.ActivityType type,
            @RequestParam(required = false) @PositiveOrZero Integer page,
            @RequestParam(required = false) @Positive Integer size
    ) {
        return centralActivitiesRepository.findAllActivities(
                new ActivityPageQuery(
                        Optional.ofNullable(status),
                        Optional.ofNullable(type),
                        Optional.ofNullable(page).orElse(Page.DEFAULT_PAGE),
                        Optional.ofNullable(size).orElse(Page.DEFAULT_SIZE)
                )
        );
    }

    @GetMapping("/{activityId}")
    public Activity getActivity(@PathVariable Integer activityId) {
        return centralActivitiesRepository.getActivity(activityId);
    }

    @GetMapping("/register")
    public Page<ActivityParticipants> getAllParticipantActivities(
            @RequestParam(required = false) @PositiveOrZero Integer page,
            @RequestParam(required = false) @Positive Integer size
    ) {
        return centralActivityParticipantsRepository.findAllRecords(
                new ActivityPageQuery(
                        null,
                        null,
                        Optional.ofNullable(page).orElse(Page.DEFAULT_PAGE),
                        Optional.ofNullable(size).orElse(Page.DEFAULT_SIZE)
                )
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activity createLibrary(@RequestBody @Valid Activity activity) {
        return centralActivitiesRepository.createActivity(activity);
    }

    @PostMapping("/register/{activityId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityParticipants createEntry(@PathVariable Integer activityId, @RequestHeader("Authorization") String authorizationHeader) {
        return centralActivityParticipantsRepository.tryToCreateARecord(centralActivitiesRepository.getActivity(activityId), authorizationHeader);
    }
}