package com.fastcampus.jober.domain.space.spacewall.spacewallpermission.domain;

import com.fastcampus.jober.domain.BaseTimeEntity;
import com.fastcampus.jober.domain.space.spacewall.spacewallmember.domain.SpaceWallMember;
import com.fastcampus.jober.global.constant.Auths;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "space_wall_permission")
@Data
@EqualsAndHashCode(callSuper = false)
public class SpaceWallPermission extends BaseTimeEntity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "space_wall_member_id")
    private SpaceWallMember spaceWallMember;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Auths auths = Auths.VIEWER;

    @Column
    private Long parentId;

    public void setAuths(Auths auths) {
        this.auths = auths;
    }

}