package com.travel.domain.tag.entity;

import jakarta.persistence.*;

import com.travel.domain.place.entity.Place;
import com.travel.domain.tagtype.entity.TagType;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_type_id")
    private TagType tagType;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "name", length = 100)
    private String name;
}
