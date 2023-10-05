package com.fastcampus.jober.domain.template.controller;

import com.fastcampus.jober.domain.template.dto.TemplateResponseDto.ListDto;
import com.fastcampus.jober.domain.template.service.MyTemplateService;
import com.fastcampus.jober.global.security.auth.session.MemberDetails;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-templates")
@RequiredArgsConstructor
public class MyTemplateController {

    private final MyTemplateService myTemplateService;

    @GetMapping
    public ResponseEntity<?> myTemplateList(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @RequestParam(required = false, defaultValue = "0") int page) {
        page = page == 0 ? 0 : page - 1;

        Page<ListDto> myTemplates = myTemplateService.findMyTemplates(page, 10,
            memberDetails.getMember());

        return ResponseEntity.ok(
            new ResponseDTO<Page<ListDto>>(HttpStatus.OK, "정상적으로 처리되었습니다.", myTemplates));
    }

    @DeleteMapping("/favorite")
    public ResponseEntity<?> myTemplateRemove(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @RequestParam(name = "templateId") Long myTemplateId) {
        myTemplateService.removeMyTemplate(memberDetails.getMember().getId(), myTemplateId);

        return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "정상적으로 처리되었습니다.", null),
            HttpStatus.OK);
    }
}
