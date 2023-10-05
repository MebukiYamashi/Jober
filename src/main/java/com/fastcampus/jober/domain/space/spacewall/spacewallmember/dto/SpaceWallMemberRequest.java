package com.fastcampus.jober.domain.space.spacewall.spacewallmember.dto;

import com.fastcampus.jober.global.constant.Auths;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SpaceWallMemberRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignDTO {

        @NotBlank(message = "이메일을 입력해주세요")
        @Pattern(
                regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$",
                message = "이메일 형식으로 작성해주세요"
        )
        private String email;

        @NotBlank(message = "권한을 선택해주세요")
        private Auths auths;
    }
}
