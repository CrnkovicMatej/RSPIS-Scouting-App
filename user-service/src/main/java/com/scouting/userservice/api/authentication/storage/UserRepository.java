package com.scouting.userservice.api.authentication.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByApiKey(String apiKey);

    Page<UserEntity> findPageByRole(UserRole role, Pageable pageable);
}