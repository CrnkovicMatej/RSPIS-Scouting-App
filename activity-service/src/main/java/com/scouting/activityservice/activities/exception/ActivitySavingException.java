package com.scouting.activityservice.activities.exception;

public final class ActivitySavingException extends ActivityRepositoryException{
    private ActivitySavingException(String message, String action, Throwable cause) {
        super(message, action, cause);
    }

    public static ActivitySavingException becauseOfExceptionDuringSaving(Throwable cause) {
        return new ActivitySavingException(
                "Error when processing the activity.",
                "Please try again later.",
                cause
        );
    }
}
