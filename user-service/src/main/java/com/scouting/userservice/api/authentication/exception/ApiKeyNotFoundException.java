package com.scouting.userservice.api.authentication.exception;

public final class ApiKeyNotFoundException extends UserServiceException{

    private ApiKeyNotFoundException(String message, String action) {
        super(message, action);
    }
    public static ApiKeyNotFoundException becauseApiKeyDoesntExist(String apiKey) {
        return new ApiKeyNotFoundException(
                "Provided apiKey " + apiKey + " does not exist.",
                "Please provide a valid api-key id."
        );
    }
}
