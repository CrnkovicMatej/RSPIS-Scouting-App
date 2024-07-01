package com.scouting.activityservice.activities.storage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "activities",
        //uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "artifact_id"})},
        schema = "activity_repository"
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity {

    public enum ActivityType {
        PERSONAL, LOCAL, CITY, NATIONAL, INTERNATIONAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Integer id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private ActivityType type;

    @Column(name = "description")
    private String description;
}
