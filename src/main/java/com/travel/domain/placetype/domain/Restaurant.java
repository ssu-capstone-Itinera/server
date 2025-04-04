package com.travel.domain.placetype.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;

import com.travel.domain.place.domain.Place;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("RESTAURANT")
@Getter
@Setter
public class Restaurant extends Place {

  
    @ElementCollection
    @CollectionTable(
            name = "restaurant_cuisines",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "cuisine")
    private List<String> cuisine;

    @Column(name = "price_level")
    private int priceLevel;

    @Column(name = "opening_hours")
    private String openingHours;

    public Map<String, Object> getDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("cuisine", cuisine);
        details.put("priceLevel", priceLevel);
        details.put("openingHours", openingHours);
        return details;
    }
}
