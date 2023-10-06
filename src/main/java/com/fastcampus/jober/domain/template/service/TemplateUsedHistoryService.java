package com.fastcampus.jober.domain.template.service;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.member.repository.MemberRepository;
import com.fastcampus.jober.domain.template.domain.Template;
import com.fastcampus.jober.domain.template.domain.TemplateUsedHistory;
import com.fastcampus.jober.domain.template.exception.TemplateNotFoundException;
import com.fastcampus.jober.domain.template.repository.TemplateRepository;
import com.fastcampus.jober.domain.template.repository.TemplateUsedHistoryRepository;
import com.fastcampus.jober.global.constant.ErrorCode;
import com.fastcampus.jober.global.error.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateUsedHistoryService {

    private final TemplateUsedHistoryRepository historyRepository;
    private final MemberRepository memberRepository;
    private final TemplateRepository templateRepository;

    @Transactional
    public void saveHistory(Long memberId, Long templateId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(
            ErrorCode.MEMBER_NOT_FOUND));

        Template template = templateRepository.findById(templateId)
            .orElseThrow(() -> new TemplateNotFoundException(ErrorCode.TEMPLATE_NOT_FOUND));

        TemplateUsedHistory oldHistory = findHistory(member, template);

        if (oldHistory != null) {
            oldHistory.updateUsedAt();
            return;
        }

        TemplateUsedHistory history = TemplateUsedHistory.builder()
            .member(member)
            .template(template)
            .build();

        historyRepository.save(history);
    }

    private TemplateUsedHistory findHistory(Member member, Template template) {
        TemplateUsedHistory history = historyRepository.findByMemberAndTemplate(member, template)
            .orElse(null);

        return history;
    }
}
