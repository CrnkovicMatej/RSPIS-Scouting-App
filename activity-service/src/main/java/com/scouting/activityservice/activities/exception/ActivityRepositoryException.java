package com.scouting.activityservice.activities.exception;

public abstract class ActivityRepositoryException extends RuntimeException{

    private final String action;

    protected ActivityRepositoryException(String message, String action) {
        super(message);
        this.action = action;
    }

    protected ActivityRepositoryException(String message, String action, Throwable cause) {
        super(message, cause);
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
