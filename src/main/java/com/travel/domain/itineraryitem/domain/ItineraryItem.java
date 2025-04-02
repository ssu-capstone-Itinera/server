package com.travel.domain.itineraryitem.domain;

import com.travel.domain.itinerary.domain.Itinerary;
import com.travel.domain.place.domain.Place;
import com.travel.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "itinerary_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itinerary_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @Column(name = "order_num")
    private Integer orderNum;

    @Column(name = "transportation")
    @Enumerated(EnumType.STRING)
    private Transportation transportation;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "cost")
    private Integer cost;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;


}