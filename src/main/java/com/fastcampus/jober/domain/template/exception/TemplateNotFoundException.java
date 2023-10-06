package com.fastcampus.jober.domain.template.exception;

import com.fastcampus.jober.global.constant.ErrorCode;
import com.fastcampus.jober.global.error.exception.DomainException;

public class TemplateNotFoundException extends DomainException {

    public TemplateNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
