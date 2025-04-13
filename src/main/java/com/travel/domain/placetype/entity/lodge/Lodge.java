package com.travel.domain.placetype.entity.lodge;

import com.travel.domain.place.entity.Place;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@DiscriminatorValue("LODGE")
@Getter
@Setter
@NoArgsConstructor
public class Lodge extends Place {

    @Column(name = "lodge_type")
    private LodgeType lodgeType;

    @Column(name = "lodge_price_range")
    private LodgePriceRange lodgePriceRange;

    @Column(name = "local_feature")
    private LocationFeature locationFeature;

    @Override
    public Map<String, Object> getDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("lodgeType", lodgeType);
        details.put("lodge price range", lodgePriceRange);
        details.put("local feature", locationFeature);
        return details;
    }

}
