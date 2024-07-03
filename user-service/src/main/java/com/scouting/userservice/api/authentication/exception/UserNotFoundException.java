package com.scouting.userservice.api.authentication.exception;

public class UserNotFoundException extends UserServiceException {
    private UserNotFoundException(String message, String action) {
        super(message, action);
    }

    public static UserNotFoundException becauseTheUserDoesNotExist(String id) {
        return new UserNotFoundException(
                "Error when trying to fetch the user by userID.",
                "Try again with correct credentials."
        );
    }
}
