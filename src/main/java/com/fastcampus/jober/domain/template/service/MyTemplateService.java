package com.fastcampus.jober.domain.template.service;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.member.repository.MemberRepository;
import com.fastcampus.jober.domain.template.domain.MyTemplate;
import com.fastcampus.jober.domain.template.domain.Template;
import com.fastcampus.jober.domain.template.dto.TemplateResponseDto;
import com.fastcampus.jober.domain.template.dto.TemplateResponseDto.ListDto;
import com.fastcampus.jober.domain.template.exception.MyTemplateNotFoundException;
import com.fastcampus.jober.domain.template.exception.TemplateException;
import com.fastcampus.jober.domain.template.repository.MyTemplateRepository;
import com.fastcampus.jober.global.constant.ErrorCode;
import com.fastcampus.jober.global.error.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyTemplateService {

    private final MyTemplateRepository myTemplateRepository;
    private final MemberRepository memberRepository;

    public Page<TemplateResponseDto.ListDto> findMyTemplates(int page, int size, Member member) {
        if (page < 0) {
            throw new TemplateException(ErrorCode.PAGE_BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Template> templatePage = myTemplateRepository.findTemplatesByMember(member, pageable);

        Page<ListDto> listDtos = templatePage.map(m -> ListDto.builder()
            .id(m.getId())
            .title(m.getTitle())
            .description(m.getDescription())
            .hashtags(m.getHashtags())
            .thumbnailUrl(m.getThumbnailImageUrl()).build()
        );

        return listDtos;
    }

    @Transactional
    public void removeMyTemplate(Long memberId, Long myTemplateId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        MyTemplate myTemplate = myTemplateRepository.findById(myTemplateId)
            .orElseThrow(() -> new MyTemplateNotFoundException(ErrorCode.MY_TEMPLATE_NOT_FOUND));

        if (!myTemplate.getMember().equals(member)) {
            throw new MemberException(ErrorCode.MY_TEMPLATE_MEMBER_NOT_MATCHED);
        }

        myTemplateRepository.delete(myTemplate);
    }
}
