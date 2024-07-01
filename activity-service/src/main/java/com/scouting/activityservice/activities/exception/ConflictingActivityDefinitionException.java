package com.scouting.activityservice.activities.exception;

public final class ConflictingActivityDefinitionException extends ActivityRepositoryException {

    private ConflictingActivityDefinitionException(String message, String action) {
        super(message, action);
    }

    public static ConflictingActivityDefinitionException becauseTheActivityAlreadyExists(Integer activityID) {
        return new ConflictingActivityDefinitionException(
                "Activity with id '" + activityID + "' already exists.",
                "Please provide a different ID or fetch the activity by its id using GET /activities/{activityId}."
        );
    }

    public static ConflictingActivityDefinitionException becauseOfRepositoryConstraintViolation() {
        return new ConflictingActivityDefinitionException(
                "There was a conflict when saving the activity.",
                "Please try again or check if the activity with the same activityID already exists."
        );
    }

}