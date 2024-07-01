package com.scouting.activityservice.activities.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, Integer> {
    Page<ActivityEntity> findPageByType(ActivityEntity.ActivityType type, Pageable pageable);
}