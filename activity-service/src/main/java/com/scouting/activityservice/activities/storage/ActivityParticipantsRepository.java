package com.scouting.activityservice.activities.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityParticipantsRepository extends JpaRepository<ActivityParticipantsEntity, Integer> {
}
