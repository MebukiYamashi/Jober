package com.fastcampus.jober.domain.template.controller;

import com.fastcampus.jober.domain.template.dto.TemplateResponseDto.ListDto;
import com.fastcampus.jober.domain.template.service.TemplateService;
import com.fastcampus.jober.global.security.auth.session.MemberDetails;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<ListDto>>> templateList(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false, defaultValue = "0") int page) {
        page = page == 0 ? 0 : page - 1;

        Page<ListDto> templates = templateService.findTemplates(memberDetails.getMember(), type,
            keyword, page, 10);

        return ResponseEntity.ok(
            new ResponseDTO<>(HttpStatus.OK, "정상적으로 처리되었습니다.", templates));
    }

    @PostMapping("/favorite")
    public ResponseEntity<ResponseDTO> templateAdd(
        @AuthenticationPrincipal MemberDetails memberDetails, @RequestParam Long templateId) {
        templateService.addTemplate(memberDetails.getMember(), templateId);

        return new ResponseEntity<>(
            new ResponseDTO(HttpStatus.CREATED, "정상적으로 처리되었습니다.", null), HttpStatus.CREATED);
    }
}
