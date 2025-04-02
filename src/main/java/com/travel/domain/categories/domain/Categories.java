package com.travel.domain.categories.domain;

import com.travel.domain.place.domain.Place;
import com.travel.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categories extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categories_id")
    private Long id;

    @Column(name = "category_name")
    @Enumerated(EnumType.STRING)
    private CategoryName categoryName;

    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<Place> places = new ArrayList<>();

    public enum CategoryName {
        RESTAURANT, CAFE, SHOPPING, ACCOMMODATION, ATTRACTION, ENTERTAINMENT, TRANSPORTATION, OTHER
    }
}