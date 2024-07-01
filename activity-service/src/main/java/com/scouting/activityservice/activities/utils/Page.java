package com.scouting.activityservice.activities.utils;

import java.util.List;

public record Page<T>(
        List<T> results,
        int page,
        int size,
        int totalPages,
        long totalResults
) {

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 20;
}