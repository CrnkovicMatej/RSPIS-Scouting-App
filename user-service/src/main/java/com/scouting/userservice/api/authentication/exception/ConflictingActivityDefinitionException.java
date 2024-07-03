package com.scouting.userservice.api.authentication.exception;

public final class ConflictingActivityDefinitionException extends UserServiceException {

    private ConflictingActivityDefinitionException(String message, String action) {
        super(message, action);
    }

    public static ConflictingActivityDefinitionException becauseTheUserAlreadyExists(Integer userID) {
        return new ConflictingActivityDefinitionException(
                "User with id '" + userID + "' already exists.",
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