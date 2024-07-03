package com.scouting.activityservice.activities.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ActivityCompositeKey implements Serializable {
    private Long activity_id;

    private Long user_id;

    ActivityCompositeKey(Long activity_id, Long user_id)
    {
        this.activity_id = activity_id;
        this.user_id = user_id;
    }

    public ActivityCompositeKey() {
        //
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityCompositeKey that = (ActivityCompositeKey) o;
        return Objects.equals(activity_id, that.activity_id) &&
                Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity_id, user_id);
    }
}
