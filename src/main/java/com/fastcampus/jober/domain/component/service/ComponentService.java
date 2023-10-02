package com.fastcampus.jober.domain.component.service;

import com.fastcampus.jober.domain.component.domain.Component;
import com.fastcampus.jober.domain.component.dto.ComponentResponse;
import com.fastcampus.jober.domain.component.repository.ComponentRepository;
import com.fastcampus.jober.domain.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.spacewall.repository.SpaceWallRepository;
import com.fastcampus.jober.global.constant.ErrorCode;
import com.fastcampus.jober.global.error.exception.TemplateException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComponentService {

    private final SpaceWallRepository spaceWallRepository;
    private final ComponentRepository componentRepository;


    /**
     * 공유페이지 조회시 컴포넌트 리스트를 반환
     * todo builder들 dto에 클래스화 시키기.
     * @param spaceWall
     * @return List<ComponentResponse.ComponentResponseDTO>
     *
     */
    @Transactional
    public List<ComponentResponse.ComponentResponseDTO> findComponentListByspaceWallId(SpaceWall spaceWall) {
        if (spaceWall == null) {
            throw new TemplateException(ErrorCode.SPACEWALL_NOT_FOUND);
        }
        List<Component> componentList = componentRepository.findComponentBySpaceWall(spaceWall);
        List<ComponentResponse.ComponentResponseDTO> responseDTOList = new ArrayList<ComponentResponse.ComponentResponseDTO>();

        for (int i = 0; i < componentList.size(); i++) {
            Component component = componentList.get(i);
            if (component.getType().equals("page") && component.getThisSpaceWall() != null) {
                responseDTOList.add(ComponentResponse.ComponentResponseDTO.builder()
                    .id(component.getId())
                    .spaceWallId(component.getParentSpaceWall().getId())
                    .childSpaceWallId(component.getThisSpaceWall().getId())
                    .type(component.getType())
                    .visible(component.isVisible())
                    .title(component.getThisSpaceWall().getTitle())
                    .content(component.getThisSpaceWall().getDescription())
                    .sequence(component.getSequence())
                    .createdAt(component.getCreatedAt())
                    .updatedAt(component.getUpdatedAt())
                    .build());

            }
            else if (component.getType().equals("temp") && component.getTemplate() != null) {
                responseDTOList.add(ComponentResponse.ComponentResponseDTO.builder()
                    .id(component.getId())
                    .spaceWallId(component.getParentSpaceWall().getId())
                    .templateId(component.getTemplate().getId())
                    .type(component.getType())
                    .visible(component.isVisible())
                    .title(component.getTemplate().getTitle())
                    .content(component.getTemplate().getDescription())
                    .sequence(component.getSequence())
                    .createdAt(component.getCreatedAt())
                    .updatedAt(component.getUpdatedAt())
                    .build());
            }
            else {
                responseDTOList.add(ComponentResponse.ComponentResponseDTO.builder()
                    .id(component.getId())
                    .spaceWallId(component.getParentSpaceWall().getId())
                    .type(component.getType())
                    .visible(component.isVisible())
                    .title(component.getTitle())
                    .content(component.getContent())
                    .sequence(component.getSequence())
                    .createdAt(component.getCreatedAt())
                    .updatedAt(component.getUpdatedAt())
                    .build());
            }
        }
        return responseDTOList;
    }


}
