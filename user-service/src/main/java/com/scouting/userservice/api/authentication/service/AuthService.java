package com.scouting.userservice.api.authentication.service;

import com.scouting.userservice.api.authentication.CentralUserRepository;
import com.scouting.userservice.api.authentication.exception.ApiKeyNotFoundException;
import com.scouting.userservice.api.authentication.storage.UserRole;
import com.scouting.userservice.api.authentication.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    private final CentralUserRepository userRepository;

    public AuthService(CentralUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String authorizationHeaderValue) {
        return readApiKey(authorizationHeaderValue)
                .map(this::apiKeyExists)
                .orElse(false);
    }

    public boolean authenticateAndAuthorize(String authorizationHeaderValue, UserRole role ){
        return readApiKey(authorizationHeaderValue)
                .flatMap(this::findUserByApiKey)
                .map(user -> user.getRole().ordinal() >= role.ordinal())
                .orElse(false);
    }

    private Optional<String> readApiKey(String authorizationHeaderValue) {
        var prefixLength = AUTHORIZATION_HEADER_PREFIX.length();

        return Optional.ofNullable(authorizationHeaderValue)
                .filter(authorizationHeader -> authorizationHeader.startsWith(AUTHORIZATION_HEADER_PREFIX))
                .filter(authorizationHeader -> authorizationHeader.length() > prefixLength)
                .map(value -> value.substring(prefixLength));
    }

    private Optional<User> findUserByApiKey(String apiKey) {
        try {
            return Optional.ofNullable(userRepository.findByApiKey(apiKey));
        } catch (Exception e) {
            throw ApiKeyNotFoundException.becauseApiKeyDoesntExist(apiKey);
        }
    }

    private boolean apiKeyExists(String apiKey) {
        try {
            return userRepository.findByApiKey(apiKey) != null;
        } catch (Exception e) {
            throw ApiKeyNotFoundException.becauseApiKeyDoesntExist(apiKey);
        }
    }
}
