package com.scouting.activityservice.activities.exception;

public final class ParticipantActivitySavingException extends ActivityRepositoryException{
    private ParticipantActivitySavingException(String message, String action) {
        super(message, action);
    }

    public static ParticipantActivitySavingException becauseUserIdIsWrong() {
        return new ParticipantActivitySavingException(
                "Activity id must not be provided.",
                "Please remove the id from the request body."
        );
    }
}
