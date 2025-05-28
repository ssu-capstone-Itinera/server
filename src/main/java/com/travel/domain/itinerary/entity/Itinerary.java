package com.travel.domain.itinerary.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.travel.domain.place.entity.MyPlace;
import com.travel.domain.place.entity.Place;
import jakarta.persistence.*;

import com.travel.domain.member.entity.Member;
import com.travel.domain.trip.entity.Trip;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.*;

@Entity
@Table(name = "itinerary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Itinerary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itinerary_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "tour_date")
    private LocalDate tourDate;

    @Column(name = "DailyTourPlace")
    private String dailyTourPlace;

    //note 사용하지 않을 듯 해서 지웠습니다.
    //@Column(name = "note", length = 1000)
    //private String note;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "itinerary_place",
            joinColumns = @JoinColumn(name = "itinerary_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id"))
    @OrderColumn(name = "place_order")
    private List<Place> places = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "itinerary_my_place",
            joinColumns = @JoinColumn(name = "itinerary_id"),
            inverseJoinColumns = @JoinColumn(name = "my_place_id"))
    @OrderColumn(name = "my_place_order")
    private List<MyPlace> myPlaces = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "itinerary_place_type_order", joinColumns = @JoinColumn(name = "itinerary_id"))
    @OrderColumn(name = "order_index")
    private List<String> itineraryPlaceTypeOrder = new ArrayList<>();


    //@OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<ItineraryItem> itineraryItems = new ArrayList<>();

}

