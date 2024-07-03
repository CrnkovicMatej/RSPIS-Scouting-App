package com.scouting.userservice.api.authentication.exception;

public final class UserSavingException extends UserServiceException{
    private UserSavingException(String message, String action, Throwable cause) {
        super(message, action, cause);
    }

    public static UserSavingException becauseOfExceptionDuringSaving(Throwable cause) {
        return new UserSavingException(
                "Error when processing the user.",
                "Please try again later.",
                cause
        );
    }
}