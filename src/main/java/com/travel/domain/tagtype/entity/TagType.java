package com.travel.domain.tagtype.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.travel.domain.tag.entity.Tag;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tag_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_type_id")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", length = 100)
    private String description;

    @OneToMany(mappedBy = "tagType", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();
}
