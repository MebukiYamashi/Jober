package com.fastcampus.jober.global.error.exception;

import com.fastcampus.jober.global.constant.ErrorCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpaceWallTempException extends DomainException{

    public SpaceWallTempException(ErrorCode errorCode) {super(errorCode);}

}
