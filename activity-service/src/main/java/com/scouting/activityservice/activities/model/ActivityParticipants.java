package com.scouting.activityservice.activities.model;

import com.scouting.activityservice.activities.storage.ActivityEntity;
import com.scouting.activityservice.activities.storage.ActivityParticipantsEntity;

public record ActivityParticipants(
        Long activity_id,

        Long user_id
) {
    public static ActivityParticipants fromEntity(ActivityParticipantsEntity entity) {

        return new ActivityParticipants(
                entity.getActivity_id(),
                entity.getUser_id()
        );
    }

}
