package com.fastcampus.jober.domain.member.dto;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.space.spacewall.spacewallmember.domain.SpaceWallMember;
import com.fastcampus.jober.global.constant.Auths;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class MemberResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberDTO {

        private Long id;
        private String email;
        private String username;
        private List<PermissionDTO> permissionDTOs;

        public MemberDTO(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
            this.username = member.getUsername();
            List<PermissionDTO> permissions = new ArrayList<>();
            for (SpaceWallMember spaceWallMember : member.getSpaceWallMember()) {
                PermissionDTO permissionDTO =
                        new PermissionDTO(
                                spaceWallMember.getSpaceWall().getId(),
                                spaceWallMember.getId(),
                                spaceWallMember.getSpaceWallPermission().getAuths()
                        );
                permissions.add(permissionDTO);
            }
            this.permissionDTOs = permissions;

        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class PermissionDTO {
        private Long spaceWallId;
        private Long spaceWallMemberId;
        private Auths auths;

        public PermissionDTO(Long spaceWallId, Long spaceWallMemberId, Auths auths) {
            this.spaceWallId = spaceWallId;
            this.spaceWallMemberId = spaceWallMemberId;
            this.auths = auths;
        }
    }

    public interface PermissionMappedDTO {

        Long getSpaceWallId();
        Long getSpaceWallMemberId();
        Auths getAuths();

    }

    @Getter
    @Setter
    public static class JoinDTO {

        private Long id;
        private String email;
        private String username;

        public JoinDTO(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
            this.username = member.getUsername();
        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class MySpaceWallDTO {
        Long spaceWallId;
        String title;
        Auths auths;
    }
}
