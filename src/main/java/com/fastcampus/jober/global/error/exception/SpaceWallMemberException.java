package com.fastcampus.jober.global.error.exception;

import com.fastcampus.jober.global.constant.ErrorCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpaceWallMemberException extends DomainException {

    public SpaceWallMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
