package com.fastcampus.jober.domain.template.exception;

import com.fastcampus.jober.global.constant.ErrorCode;
import com.fastcampus.jober.global.error.exception.DomainException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TemplateException extends DomainException {

    public TemplateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
