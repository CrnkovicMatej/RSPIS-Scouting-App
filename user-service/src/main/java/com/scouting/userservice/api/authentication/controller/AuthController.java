package com.scouting.userservice.api.authentication.controller;

import com.scouting.userservice.api.authentication.CentralUserRepository;
import com.scouting.userservice.api.authentication.model.User;
import com.scouting.userservice.api.authentication.service.AuthService;
import com.scouting.userservice.api.authentication.storage.UserRole;
import com.scouting.userservice.api.authentication.utils.Page;
import com.scouting.userservice.api.authentication.utils.UserPageQuery;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    private final CentralUserRepository centralUserRepository;
    private final AuthService authService;

    public AuthController(CentralUserRepository centralUserRepository, AuthService authService ) {
        this.centralUserRepository = centralUserRepository;
        this.authService = authService;
    }

    @GetMapping("/validateRole")
    public Boolean validateRole(@RequestHeader("Authorization") String authHeader, @RequestParam UserRole role) {
        return authService.authenticateAndAuthorize(authHeader, role);
    }
}
