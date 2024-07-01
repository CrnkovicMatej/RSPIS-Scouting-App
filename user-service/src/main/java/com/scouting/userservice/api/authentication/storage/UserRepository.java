package com.scouting.userservice.api.authentication.storage;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByApiKey(String apiKey);
}