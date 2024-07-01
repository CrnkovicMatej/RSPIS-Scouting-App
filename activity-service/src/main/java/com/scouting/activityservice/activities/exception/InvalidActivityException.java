package com.scouting.activityservice.activities.exception;

public final class InvalidActivityException extends ActivityRepositoryException{
    private InvalidActivityException(String message, String action) {
        super(message, action);
    }

    public static InvalidActivityException becauseTheIdIsProvided() {
        return new InvalidActivityException(
                "Activity id must not be provided.",
                "Please remove the id from the request body."
        );
    }
}
