package com.travel.domain.tag.domain;

import com.travel.domain.place.domain.Place;
import com.travel.domain.tagtype.domain.TagType;
import com.travel.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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