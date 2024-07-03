package com.scouting.userservice.api.authentication.exception;

public abstract class UserServiceException extends RuntimeException{

    private final String action;

    protected UserServiceException(String message, String action) {
        super(message);
        this.action = action;
    }

    protected UserServiceException(String message, String action, Throwable cause) {
        super(message, cause);
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}