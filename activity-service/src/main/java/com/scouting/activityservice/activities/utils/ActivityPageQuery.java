package com.scouting.activityservice.activities.utils;

import com.scouting.activityservice.activities.storage.ActivityEntity;

import java.util.Optional;
public record ActivityPageQuery (
        Optional<ActivityEntity.ActivityType> type,
        int page,
        int size
) {
}
