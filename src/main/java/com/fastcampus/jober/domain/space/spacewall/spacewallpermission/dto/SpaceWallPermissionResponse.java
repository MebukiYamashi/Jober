package com.fastcampus.jober.domain.space.spacewall.spacewallpermission.dto;

import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.domain.SpaceWallPermission;
import com.fastcampus.jober.global.constant.Auths;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceWallPermissionResponse {
    private Long id;
    private Auths auths;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long parentId;

    public SpaceWallPermissionResponse(SpaceWallPermission spaceWallPermission) {
        this.id = spaceWallPermission.getId();
        this.auths = spaceWallPermission.getAuths();
        this.createdAt = spaceWallPermission.getCreatedAt();
        this.updatedAt = spaceWallPermission.getUpdatedAt();
        this.parentId = spaceWallPermission.getParentId();
    }
}