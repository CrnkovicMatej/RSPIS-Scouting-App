package com.scouting.activityservice.activities.model;

import com.scouting.activityservice.activities.storage.ActivityEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
public record Activity (
        Integer id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal price,
        ActivityEntity.ActivityType type,

        ActivityEntity.ActivityStatus status,

        String description
){
    public static Activity fromEntity(ActivityEntity entity) {

        return new Activity(
                entity.getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPrice(),
                entity.getType(),
                entity.getStatus(),
                entity.getDescription()
        );
    }
}
