package com.fastcampus.jober.domain.template.service;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.template.domain.MyTemplate;
import com.fastcampus.jober.domain.template.domain.Template;
import com.fastcampus.jober.domain.template.dto.TemplateResponseDto.ListDto;
import com.fastcampus.jober.domain.template.exception.TemplateException;
import com.fastcampus.jober.domain.template.exception.TemplateNotFoundException;
import com.fastcampus.jober.domain.template.repository.MyTemplateRepository;
import com.fastcampus.jober.domain.template.repository.TemplateRepository;
import com.fastcampus.jober.domain.template.repository.TemplateTypeRepository;
import com.fastcampus.jober.domain.template.repository.TemplateUsedHistoryRepository;
import com.fastcampus.jober.global.constant.ErrorCode;
import com.fastcampus.jober.global.constant.TemplateCategory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateTypeRepository templateTypeRepository;
    private final MyTemplateRepository myTemplateRepository;
    private final TemplateUsedHistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public Page<ListDto> findTemplates(Member member, String type, String keyword, int page,
        int size) {
        if (page < 0) {
            throw new TemplateException(ErrorCode.PAGE_BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Template> templatePage = findTemplatePage(member, type, keyword, pageable);

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
    public void addTemplate(Member member, Long templateId) {
        Template template = templateRepository.findById(templateId)
            .orElseThrow(() -> new TemplateNotFoundException(ErrorCode.TEMPLATE_NOT_FOUND));

        MyTemplate myTemplate = MyTemplate.builder().member(member).template(template).build();

        myTemplateRepository.save(myTemplate);
    }

    private Page<Template> findTemplatePage(Member member, String type, String keyword,
        Pageable pageable) {

        if (StringUtils.isBlank(type) && StringUtils.isBlank(keyword)) {
            return templateRepository.findAll(pageable);
        }

        if (StringUtils.isBlank(type) && !StringUtils.isBlank(keyword)) {
            return templateRepository.findAllByKeywordContains(keyword, pageable);
        }

        //@TODO: 컨트롤러 단에서 처리하기
        TemplateCategory templateCategory = TemplateCategory.findByName(type);

        if (templateCategory == TemplateCategory.RECENT) {
            return historyRepository.findAllByMember(member, pageable);
        }

        if (!StringUtils.isBlank(type) && StringUtils.isBlank(keyword)) {
            return templateTypeRepository.findByTemplateCategory(templateCategory, pageable);
        }

        return templateTypeRepository.findAllByKeywordAndType(keyword, templateCategory,
            pageable);
    }
}
