package com.fastcampus.jober.domain.componentTemp.service;

import com.fastcampus.jober.domain.component.domain.Component;
import com.fastcampus.jober.domain.component.repository.ComponentRepository;
import com.fastcampus.jober.domain.componentTemp.domain.ComponentTemp;
import com.fastcampus.jober.domain.componentTemp.dto.ComponentTempRequest;
import com.fastcampus.jober.domain.componentTemp.dto.ComponentTempResponse;
import com.fastcampus.jober.domain.componentTemp.dto.ComponentTempResponse.ComponentTempResponseDTO;
import com.fastcampus.jober.domain.componentTemp.repository.ComponentTempRepository;
import com.fastcampus.jober.domain.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.spacewall.domain.SpaceWallTemp;
import com.fastcampus.jober.domain.spacewall.repository.SpaceWallRepository;
import com.fastcampus.jober.domain.spacewall.repository.SpaceWallTempRepository;
import com.fastcampus.jober.domain.template.domain.Template;
import com.fastcampus.jober.domain.template.repository.TemplateRepository;
import com.fastcampus.jober.global.constant.ErrorCode;
import com.fastcampus.jober.global.error.exception.ComponentTempException;
import com.fastcampus.jober.global.error.exception.TemplateException;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    public ComponentTempResponseDTO addComponentTemp(
        ComponentTempRequest.AddDTO addDTO) {
        if (addDTO == null) {
            throw new ComponentTempException(ErrorCode.DTO_NOT_EXISTS);
        }
        String type = addDTO.getType();
        if (!(type.equals("text") || type.equals("line") || type.equals("link") || type.equals(
            "template") || type.equals("page"))) {
            throw new ComponentTempException(ErrorCode.INVALID_COMPONENT_TYPE);
        }



        SpaceWallTemp spaceWallTemp = spaceWallTempRepository.findById(addDTO.getSpaceWallTempId()).get(); // 예외처리



        ComponentTemp componentTemp = ComponentTemp.builder()
            .spaceWallTemp(spaceWallTemp)
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

        ComponentTemp componentTemp = componentTempRepository.findById(modifyDTO.getId()).get();


        String type = modifyDTO.getType();

        if (type.equals("page")) {
            // 페이지의 경우 생성되면 컴포넌트 바로 생성되게 하는게 좋을듯 함....
            //성욱님이랑 프론트랑 이야기 해보기

            SpaceWall childSpaceWall = spaceWallRepository.findById(
                modifyDTO.getChildSpaceWallId()).get();

            componentTemp.setChildSpaceWall(childSpaceWall);

            return ComponentTempResponseDTO.toDTOPageType(componentTemp);
        } else if (type.equals("template")) {
            Template template = templateRepository.findById(modifyDTO.getTemplateId()).get();

            componentTemp.setTemplate(template);
            return ComponentTempResponseDTO.toDTOTemplateType(componentTemp);
        } else {
            componentTemp.setTitle(modifyDTO.getTitle());
            componentTemp.setContent(modifyDTO.getContent());
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
        if (type.equals("tempalte") || type.equals("page")) {
            throw new ComponentTempException(ErrorCode.INVALID_COMPONENTTYPE);
        }

        return ComponentTempResponseDTO.toDTO(componentTemp);


    }

}
