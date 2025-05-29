package com.travel.domain.place.entity;

import java.util.List;

import com.travel.domain.member.entity.Member;
import jakarta.persistence.*;

import com.travel.domain.categories.entity.Category;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.*;

@Entity
@Table(name = "my_place")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MyPlace extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_place_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

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

}
