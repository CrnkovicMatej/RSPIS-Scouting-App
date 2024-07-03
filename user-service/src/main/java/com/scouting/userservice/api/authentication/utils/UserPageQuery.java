package com.scouting.userservice.api.authentication.utils;

import com.scouting.userservice.api.authentication.storage.UserRole;

import java.util.Optional;

public record UserPageQuery (
        int page,
        int size,
        Optional<UserRole> role
){
}
