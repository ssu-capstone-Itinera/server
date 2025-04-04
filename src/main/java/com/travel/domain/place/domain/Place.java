package com.travel.domain.place.domain;

import com.travel.domain.categories.domain.Category;
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
import java.util.Map;

@Entity
@Table(name = "place")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "place_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Place extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

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

    public abstract Map<String, Object> getDetails();
}

