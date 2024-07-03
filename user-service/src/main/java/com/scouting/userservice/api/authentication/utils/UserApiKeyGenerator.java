package com.scouting.userservice.api.authentication.utils;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class UserApiKeyGenerator implements Serializable, CharSequence  {

    private String apiKey;

    public UserApiKeyGenerator() {
        this.apiKey = generateApiKey();
    }
    public String generateApiKey() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }

    @Override
    public int length() {
        return apiKey.length();
    }

    @Override
    public char charAt(int index) {
        return apiKey.charAt(index);
    }

    @Override
    public  CharSequence subSequence(int start, int end) {
        return apiKey.subSequence(start, end);
    }

    @Override
    public String toString() {
        return apiKey;
    }
}
