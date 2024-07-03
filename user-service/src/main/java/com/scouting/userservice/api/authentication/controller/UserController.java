package com.scouting.userservice.api.authentication.controller;

import com.scouting.userservice.api.authentication.CentralUserRepository;
import com.scouting.userservice.api.authentication.model.User;
import com.scouting.userservice.api.authentication.model.UserUpdate;
import com.scouting.userservice.api.authentication.storage.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/getAll")
    public Page<User> getAllUsers(
            @RequestParam(required = false) @PositiveOrZero Integer page,
            @RequestParam(required = false) @Positive Integer size,
            @RequestParam(required = false) UserRole role
    ) {
        return centralUserRepository.getAllUsers(
                new UserPageQuery(
                        Optional.ofNullable(page).orElse(Page.DEFAULT_PAGE),
                        Optional.ofNullable(size).orElse(Page.DEFAULT_SIZE),
                        Optional.ofNullable(role)
                )
        );
    }

    @GetMapping("/{userId}")
    public User getAllUsers(
            @PathVariable Long userId
    ) {
        return centralUserRepository.getUser(userId);
    }

    @GetMapping("")
    public Long getUserIdByApiKey(
            @RequestParam String apiKey
    ) {
        return centralUserRepository.findByApiKey(apiKey).id();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user) {
        return centralUserRepository.createUser(user);
    }


    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public User modifyUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdate userUpdate
    ) {
        return centralUserRepository.modifyUser(userId, userUpdate );
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLibrary(@PathVariable Long userId) {
        centralUserRepository.deleteUser(userId);
    }

}
