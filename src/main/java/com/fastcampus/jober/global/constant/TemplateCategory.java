package com.fastcampus.jober.global.constant;

import com.fastcampus.jober.domain.template.exception.TemplateException;
import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateCategory {
    RECENT("최근"), PERSONAL("개인"), COMPANY("회사"), SURVEY("설문"),
    CONTRACT("계약"), LAW("법률");

    private String name;

    public static TemplateCategory findByName(String name) {
        return Arrays.stream(TemplateCategory.values())
            .filter(category -> category.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new TemplateException(ErrorCode.TEMPLATE_TYPE_NOT_FOUND));
    }
}
