package com.travel.domain.tripshare.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.travel.domain.trip.entity.Trip;
import com.travel.global.common.entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trip_share")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripShare extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_share_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private Permission permission;

    @Column(name = "shared_at")
    private LocalDateTime sharedAt;

    public enum Permission {
        READ,
        EDIT,
        ADMIN
    }
}
