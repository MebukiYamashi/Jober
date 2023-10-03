package com.fastcampus.jober.domain.componenthistory.domain;

import com.fastcampus.jober.domain.BaseTimeEntity;
import com.fastcampus.jober.global.constant.Type;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "component_history")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ComponentHistory extends BaseTimeEntity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long templateId;

    private Long spaceWallHistoryId;

    private Long thisSpaceWallId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private boolean visible;

    @Column(length = 100)
    private String title;

    @Column(length = 1000)
    private String content;

    @Column(nullable = false)
    private int sequence;

    private Long parentSpaceWallId;

}
