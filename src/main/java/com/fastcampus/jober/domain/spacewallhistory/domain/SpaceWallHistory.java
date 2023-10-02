package com.fastcampus.jober.domain.spacewallhistory.domain;

import com.fastcampus.jober.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "space_wall_history")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpaceWallHistory extends BaseTimeEntity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long spaceWallId;

    @Column(length = 100)
    private String url;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String description;

    @Column(length = 200)
    private String profileImageUrl;

    @Column(length = 200)
    private String backgroundImageUrl;

    @Column(length = 100)
    private String pathIds;

    @Column(length = 100)
    private boolean authorized;

    @Column(nullable = false)
    private int sequence;

    private Long createMemberId;

    private Long parentSpaceWallId;
}
