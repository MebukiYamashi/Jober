package com.fastcampus.jober.domain.space.spacewall.spacewallpermission.dto;

import com.fastcampus.jober.global.constant.Auths;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceWallPermissionRequest {
    private Long spaceWallId;
    private Long memberId;
    private Auths auths;
    private Long targetSequence;
}