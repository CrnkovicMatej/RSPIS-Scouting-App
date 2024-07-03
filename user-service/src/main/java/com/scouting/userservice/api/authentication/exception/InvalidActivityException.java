package com.scouting.userservice.api.authentication.exception;

public final class InvalidActivityException extends UserServiceException {
    private InvalidActivityException(String message, String action) {
        super(message, action);
    }
    public static InvalidActivityException becauseTheIdIsProvided(String id) {
        return new InvalidActivityException(
                "You provided the id " + id + " which is not a supported behaviour. ",
                "Please provide new user without ID. It will be created automatically."
        );
    }

    public static InvalidActivityException becauseTheApiKeyIsProvided(String apiKey) {
        return new InvalidActivityException(
                "You provided the apiKey " + apiKey + " which is not a supported behaviour. ",
                "Please provide new user without apiKey. It will be created automatically."
        );
    }
}
