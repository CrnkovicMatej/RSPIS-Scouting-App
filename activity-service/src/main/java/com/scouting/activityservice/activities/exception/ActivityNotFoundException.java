package com.scouting.activityservice.activities.exception;

public final class ActivityNotFoundException extends ActivityRepositoryException{

    private ActivityNotFoundException(String message, String action) {
        super(message, action);
    }

    public static ActivityNotFoundException becauseTheActivityDoesNotExist(Integer activityID) {
        return new ActivityNotFoundException(
                "Activity with id " + activityID + " does not exist.",
                "Please provide a valid activity id or create a new activity using POST /activities."
        );
    }
}
