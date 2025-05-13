package com.travel.domain.place.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.travel.domain.categories.entity.Category;
import com.travel.domain.itineraryitem.entity.ItineraryItem;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.*;

@Entity
@Table(name = "place")
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "place_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Place extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "lat", nullable = false)
    private double lat;

    @Column(name = "lng", nullable = false)
    private double lng;


    @Column(name = "place_google_id", length = 100)
    private String placeGoogleId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;


    @Column(name = "website", length = 100)
    private String webSite;


    @Column(name = "opening_hours", length = 100)
    private List<String> openingHours;


    @Column(name = "price_level", length = 100)
    private String priceLevel;


    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "reviews")
    private List<String> reviews;

}
