package com.fastcampus.jober.global.error.exception;

import com.fastcampus.jober.global.constant.ErrorCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpaceWallException extends DomainException {

    public SpaceWallException(ErrorCode errorCode) { super(errorCode); }

    public SpaceWallException(ErrorCode errorCode, String message) { super(errorCode); }

    public SpaceWallException(String message) { this(ErrorCode.BAD_SPACE_WALL_REQUEST); }

    public static SpaceWallException invalidTargetSequence(String message) {
        return new SpaceWallException(ErrorCode.INVALID_TARGET_SEQUENCE, message);
    }
}
