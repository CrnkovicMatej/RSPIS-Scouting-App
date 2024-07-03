package com.scouting.activityservice.activities.utils;

import java.util.Optional;

public class ApiKeyReader {
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    private final String key;
    public ApiKeyReader(String key){
        this.key = key;
    }

    public Optional<String> readApiKey() {
        var prefixLength = AUTHORIZATION_HEADER_PREFIX.length();

        return Optional.ofNullable(key)
                .filter(key -> key.startsWith(AUTHORIZATION_HEADER_PREFIX))
                .filter(key -> key.length() > prefixLength)
                .map(value -> value.substring(prefixLength));
    }
}
