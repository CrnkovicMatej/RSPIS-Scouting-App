package com.scouting.userservice.api.authentication.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //Optional<UserEntity> findByApiKey(String apiKey);
}