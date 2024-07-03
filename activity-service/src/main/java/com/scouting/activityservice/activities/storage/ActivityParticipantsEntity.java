package com.scouting.activityservice.activities.storage;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

// composite primary keys: https://www.baeldung.com/jpa-composite-primary-keys
@Entity
@Table(
        name = "activity_participants",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"activity_id", "user_id"})},
        schema = "activity_service_repository"
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ActivityCompositeKey.class)
public class ActivityParticipantsEntity {

    @Id
    //@Column(name = "activity_id")
    private Long activity_id;

    @Id
    //@Column(nullable = false, name = "user_id")
    private Long user_id;
}
