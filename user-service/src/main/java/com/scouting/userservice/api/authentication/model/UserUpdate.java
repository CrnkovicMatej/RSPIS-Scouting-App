package com.scouting.userservice.api.authentication.model;

import com.scouting.userservice.api.authentication.storage.UserRole;
import jakarta.validation.constraints.Null;

public record UserUpdate(
        @Null(message = CAN_NOT_BE_MODIFIED) Integer id,
        @Null(message = CAN_NOT_BE_MODIFIED) String apiKey,
        String username,
        UserRole role
) {
    private static final String CAN_NOT_BE_MODIFIED = "can't be modified";
}
