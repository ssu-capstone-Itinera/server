package com.travel.domain.place.domain;

import com.travel.domain.categories.domain.Categories;
import com.travel.domain.itineraryitem.domain.ItineraryItem;
import com.travel.domain.tag.domain.Tag;
import com.travel.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "place")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<ItineraryItem> itineraryItems = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();
}

