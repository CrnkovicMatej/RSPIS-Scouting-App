package com.scouting.userservice.api.authentication.controller;

import com.scouting.userservice.api.authentication.CentralUserRepository;
import com.scouting.userservice.api.authentication.model.User;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.scouting.userservice.api.authentication.utils.Page;
import com.scouting.userservice.api.authentication.utils.UserPageQuery;

import java.util.Optional;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    private final CentralUserRepository centralUserRepository;

    public UserController(CentralUserRepository centralUserRepository) {
        this.centralUserRepository = centralUserRepository;
    }

    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(required = false) @PositiveOrZero Integer page,
            @RequestParam(required = false) @Positive Integer size
    ) {
        return centralUserRepository.getAllUsers(
                new UserPageQuery(
                        Optional.ofNullable(page).orElse(Page.DEFAULT_PAGE),
                        Optional.ofNullable(size).orElse(Page.DEFAULT_SIZE)
                )
        );
    }

}
