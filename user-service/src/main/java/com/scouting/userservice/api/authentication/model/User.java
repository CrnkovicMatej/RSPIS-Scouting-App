package com.scouting.userservice.api.authentication.model;

import com.scouting.userservice.api.authentication.storage.UserEntity;
import com.scouting.userservice.api.authentication.storage.UserRole;

public record User(
        Long id,
        String apiKey,
        String username,
        UserRole role
) {
    public static User fromEntity(UserEntity entity){
        return new User(
                entity.getId(),
                entity.getApiKey(),
                entity.getUsername(),
                entity.getRole()
        );
    }

    public UserRole getRole(){
        return role;
    }
}
