package com.fastcampus.jober.domain.space.component.componentTemp.service;

import com.fastcampus.jober.domain.space.component.domain.Component;
import com.fastcampus.jober.domain.space.component.repository.ComponentRepository;
import com.fastcampus.jober.domain.space.component.componentTemp.domain.ComponentTemp;
import com.fastcampus.jober.domain.space.component.componentTemp.dto.ComponentTempRequest;
import com.fastcampus.jober.domain.space.component.componentTemp.dto.ComponentTempResponse.ComponentTempResponseDTO;
import com.fastcampus.jober.domain.space.component.componentTemp.repository.ComponentTempRepository;
import com.fastcampus.jober.domain.space.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.domain.SpaceWallTemp;
import com.fastcampus.jober.domain.space.spacewall.repository.SpaceWallRepository;
import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.repository.SpaceWallTempRepository;
import com.fastcampus.jober.domain.template.domain.Template;
import com.fastcampus.jober.domain.template.repository.TemplateRepository;
import com.fastcampus.jober.global.constant.ErrorCode;
import com.fastcampus.jober.global.error.exception.ComponentTempException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComponentTempService {

    private final ComponentTempRepository componentTempRepository;
    private final SpaceWallTempRepository spaceWallTempRepository;
    private final TemplateRepository templateRepository;
    private final SpaceWallRepository spaceWallRepository;
    private final ComponentRepository componentRepository;

    @Transactional
    public ComponentTempResponseDTO addComponentTemp(
        ComponentTempRequest.AddDTO addDTO) {
        if (addDTO == null) {
            throw new ComponentTempException(ErrorCode.DTO_NOT_EXISTS);
        }

        String type = addDTO.getType();

        if (!(type.equals("cont") || type.equals("temp") || type.equals("link") || type.equals(
            "line") || type.equals("page"))) {
            throw new ComponentTempException(ErrorCode.INVALID_COMPONENT_TYPE);
        }

        SpaceWallTemp parentSpaceWallTemp = spaceWallTempRepository.findSpaceWallTempBySpaceWallId(
            addDTO.getSpaceWallId());

        ComponentTemp componentTemp = ComponentTemp.builder()
            .parentSpaceWallTemp(parentSpaceWallTemp)
            .type(addDTO.getType())
            .visible(false)
            .sequence(addDTO.getSequence())
            .deleted(false)
            .build();

        ComponentTemp addedcomponentTemp = componentTempRepository.save(componentTemp); // 예외처리

        ComponentTempResponseDTO componentTempResponseDTO = ComponentTempResponseDTO.toDTO(addedcomponentTemp);

        return componentTempResponseDTO;
    }

    @Transactional
    public ComponentTempResponseDTO modifyComponentTemp(
        ComponentTempRequest.ModifyDTO modifyDTO) {
        if (modifyDTO == null) {
            throw new ComponentTempException(ErrorCode.DTO_NOT_EXISTS);
        }

        ComponentTemp componentTemp = componentTempRepository.findById(modifyDTO.getComponentTempId()).get();


        String type = componentTemp.getType();

        if (type.equals("page")) { // 페이지타입
            SpaceWall thisSpaceWall = spaceWallRepository.findById(modifyDTO.getThisSpaceWallId()).get();

            componentTemp.setThisSpaceWall(thisSpaceWall);

//            컴포넌트 바로 생성...
            Component component = Component.builder()
                .parentSpaceWall(componentTemp.getParentSpaceWallTemp().getSpaceWall())
                .thisSpaceWall(componentTemp.getThisSpaceWall())
                .type(componentTemp.getType())
                .visible(componentTemp.isVisible())
                .sequence(componentTemp.getSequence())
                .build();

            componentRepository.save(component);

            return ComponentTempResponseDTO.toDTOPageType(componentTemp);
        } else if (type.equals("temp")) { // 템플릿 타입
            Template template = templateRepository.findById(modifyDTO.getTemplateId()).get();

            componentTemp.setTemplate(template);
            return ComponentTempResponseDTO.toDTOTemplateType(componentTemp);
        } else if (type.equals("cont")){ // 콘텐츠 타입
            componentTemp.setContent(modifyDTO.getContent());
            return ComponentTempResponseDTO.toDTO(componentTemp);
        } else if (type.equals("link")) { // 링크 타입
            componentTemp.setTitle(modifyDTO.getTitle());
            componentTemp.setContent(modifyDTO.getContent());
            return ComponentTempResponseDTO.toDTO(componentTemp);
        }
        else { // line 타입
            return ComponentTempResponseDTO.toDTO(componentTemp);
        }
    }

    @Transactional(readOnly = true)
    public boolean checkComponentTempExists(Long id) {
        return componentTempRepository.existsComponentTempById(id);
    }

    @Transactional
    public ComponentTempResponseDTO findComponentTemp(Long componentTempId) {
        ComponentTemp componentTemp = componentTempRepository.findById(componentTempId).get();

        //페이지, 템플릿 타입인 경우 예외처리 추가하기
        String type = componentTemp.getType();
        if (type.equals("temp") || type.equals("page") || type.equals("line")) {
            throw new ComponentTempException(ErrorCode.CANT_SHOW_COMPENTTEMP);
        }

        return ComponentTempResponseDTO.toDTO(componentTemp);
    }

}
