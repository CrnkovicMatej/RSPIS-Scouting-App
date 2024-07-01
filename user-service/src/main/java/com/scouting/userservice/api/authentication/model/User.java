package com.scouting.userservice.api.authentication.model;

import com.scouting.userservice.api.authentication.storage.UserEntity;
import com.scouting.userservice.api.authentication.storage.UserRole;

public record User(
        Long id,
        String userName,
        UserRole role
) {
    public static User fromEntity(UserEntity entity){
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getRole()
        );
    }
}
