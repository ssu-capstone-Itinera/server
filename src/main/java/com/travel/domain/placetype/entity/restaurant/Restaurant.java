//package com.travel.domain.placetype.entity.restaurant;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.DiscriminatorValue;
//import jakarta.persistence.Entity;
//
//import com.travel.domain.place.entity.Place;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@DiscriminatorValue("RESTAURANT")
//@Getter
//@Setter
//@NoArgsConstructor
//public class Restaurant extends Place {
//
//    @Column(name = "restaurant_type")
//    private RestaurantType restaurantType;
//
//    @Column(name = "restaurant_price_range")
//    private RestaurantPriceRange restaurantPriceRange;
//
//    @Override
//    public Map<String, Object> getDetails() {
//        Map<String, Object> details = new HashMap<>();
//        details.put("restaurant type", restaurantType);
//        details.put("restaurant price range", restaurantType);
//        return details;
//    }
//}
