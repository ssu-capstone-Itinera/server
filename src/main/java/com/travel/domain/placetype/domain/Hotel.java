package com.travel.domain.placetype.domain;

import com.travel.domain.place.domain.Place;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@DiscriminatorValue("HOTEL")
@Getter
@Setter
@NoArgsConstructor
public class Hotel extends Place {

    @Column(name = "star_rating")
    private Integer starRating;


    @Override
    public Map<String, Object> getDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("starRating", starRating);
        return details;
    }
}