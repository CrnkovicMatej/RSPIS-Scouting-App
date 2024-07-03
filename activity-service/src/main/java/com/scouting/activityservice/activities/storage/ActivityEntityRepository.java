package com.scouting.activityservice.activities.storage;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, Integer> {

    Page<ActivityEntity> findPageByType(ActivityEntity.ActivityType type, Pageable pageable);

    Page<ActivityEntity> findPageByStatus(ActivityEntity.ActivityStatus status, Pageable pageable);

}