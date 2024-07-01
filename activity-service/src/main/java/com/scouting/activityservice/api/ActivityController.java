package com.scouting.activityservice.api;

import java.util.List;

import com.scouting.activityservice.activities.CentralActivitiesRepository;
import com.scouting.activityservice.activities.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final CentralActivitiesRepository centralActivitiesRepository;

    public ActivityController(CentralActivitiesRepository centralActivitiesRepository) {
        this.centralActivitiesRepository = centralActivitiesRepository;
    }

    @GetMapping("/pong/1")
    public ResponseEntity<String> pong() {
        return ResponseEntity.ok("PONG");
    }

    @GetMapping("/{activityId}")
    public Activity getActivity(@PathVariable Integer activityId) {
        return centralActivitiesRepository.getActivity(activityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activity createLibrary(@RequestBody @Valid Activity activity) {
        return centralActivitiesRepository.createActivity(activity);
    }
}