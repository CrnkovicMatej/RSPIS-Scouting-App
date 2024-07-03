package com.scouting.activityplanningservice.api;

import com.scouting.activityplanningservice.service.PlanningService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities/planning")
public class ApplicationPlanningController {
    private final PlanningService planningService;

    public ApplicationPlanningController(PlanningService planningService) {
        this.planningService = planningService;
    }

    @GetMapping("/plan")
    public String planActivity(
            @RequestParam @NotNull String topic,
            @RequestParam(required = false) @Pattern(regexp = "(?i)INDOOR|OUTDOOR") String placeType,
            @RequestParam(required = false) @Pattern(regexp = "(?i)INDIVIDUAL|SMALL_GROUP|LARGE_GROUP") String numberOfPeople,
            @RequestParam(required = false) @Pattern(regexp = "(?i)CUBS|BEAVERS|SCOUTS|EXPLORERS") String ageGroup,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) Boolean withEquipment) {
        return planningService.planActivity(topic, placeType, numberOfPeople, ageGroup, priceRange, withEquipment);
    }
}
