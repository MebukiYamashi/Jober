package com.fastcampus.jober.domain.space.spacewall.spacewalltemp.service;

import com.fastcampus.jober.domain.space.component.domain.Component;
import com.fastcampus.jober.domain.space.component.repository.ComponentRepository;
import com.fastcampus.jober.domain.space.component.componentTemp.domain.ComponentTemp;
import com.fastcampus.jober.domain.space.component.componentTemp.dto.ComponentTempRequest.ModifyDTOInSWT;
import com.fastcampus.jober.domain.space.component.componentTemp.dto.ComponentTempResponse;
import com.fastcampus.jober.domain.space.component.componentTemp.repository.ComponentTempRepository;
import com.fastcampus.jober.domain.space.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.space.spacewall.dto.SpaceWallResponse.ResponseDto;
import com.fastcampus.jober.domain.space.spacewall.repository.SpaceWallRepository;
import com.fastcampus.jober.domain.space.spacewall.service.SpaceWallService;
import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.dto.SpaceWallTempRequest.ModifyDTO;
import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.domain.SpaceWallTemp;
import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.dto.SpaceWallTempResponse.SpaceWallTempResponseDTO;
import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.repository.SpaceWallTempRepository;
import com.fastcampus.jober.domain.template.service.TemplateUsedHistoryService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceWallTempService {

    private final SpaceWallTempRepository spaceWallTempRepository;
    private final SpaceWallRepository spaceWallRepository;
    private final ComponentRepository componentRepository;
    private final ComponentTempRepository componentTempRepository;
    private final SpaceWallService spaceWallService;
    private final TemplateUsedHistoryService templateUsedHistoryService;


    @Transactional
    public boolean checkSpaceWallTempExists(Long spaceWallId) {
        return spaceWallTempRepository.existsSpaceWallTempBySpaceWall(spaceWallId);
    }


    @Transactional
    public SpaceWallTempResponseDTO addSpaceWallTemp(Long spaceWallId) {
        SpaceWall spaceWall = spaceWallRepository.findById(spaceWallId).get();

        SpaceWallTemp spaceWallTemp = SpaceWallTemp.builder()
            .spaceWall(spaceWall)
            .title(spaceWall.getTitle())
            .description(spaceWall.getDescription())
            .profileImageUrl(spaceWall.getProfileImageUrl())
            .backgroundImageUrl(spaceWall.getBackgroundImageUrl())
            .sequence(spaceWall.getSequence())
            .build();

        SpaceWallTemp savedSWT = spaceWallTempRepository.save(spaceWallTemp);

        List<Component> componentList = componentRepository.findComponentBySpaceWall(spaceWall);

        for (int i = 0; i < componentList.size(); i++) {
            Component component = componentList.get(i);
            ComponentTemp componentTemp = ComponentTemp.builder()
                .parentSpaceWallTemp(savedSWT)
                .thisSpaceWall(component.getThisSpaceWall())
                .template(component.getTemplate())
                .componentId(component.getId())
                .type(component.getType())
                .visible(component.isVisible())
                .title(component.getTitle())
                .content(component.getContent())
                .sequence(component.getSequence())
                .build();

            componentTempRepository.save(componentTemp);
        }
        return findSpaceWallTemp(spaceWallId);
    }

    @Transactional
    public SpaceWallTempResponseDTO findSpaceWallTemp(Long spaceWallId) {
        SpaceWallTemp spaceWallTemp = spaceWallTempRepository.findSpaceWallTempBySpaceWallId(
            spaceWallId);

        List<ComponentTemp> componentTempList = componentTempRepository.findComponentTempBySpaceWallTemp(
            spaceWallTemp);

        return SpaceWallTempResponseDTO.builder()
            .spaceWallTempId(spaceWallTemp.getId())
            .spaceWallId(spaceWallTemp.getSpaceWall().getId())
            .title(spaceWallTemp.getTitle())
            .description(spaceWallTemp.getDescription())
            .profileImageUrl(spaceWallTemp.getProfileImageUrl())
            .backgroundImageUrl(spaceWallTemp.getBackgroundImageUrl())
            .sequence(spaceWallTemp.getSequence())
            .createdAt(spaceWallTemp.getCreatedAt())
            .updateddAt(spaceWallTemp.getUpdatedAt())
            .componentTempList(
                ComponentTempResponse.ComponentTempResponseDTO.listToDTO(componentTempList))
            .build();
    }


    @Transactional
    public SpaceWallTempResponseDTO modifySpaceWallTemp(Long spaceWallId, ModifyDTO modifyDTO) {

//
        SpaceWallTemp spaceWallTemp = spaceWallTempRepository.findById(
            modifyDTO.getSpaceWallTempId()).get();

        spaceWallTemp.update(modifyDTO);

        List<ModifyDTOInSWT> componentTempList = modifyDTO.getComponentTempList();

        for (int i = 0; i < componentTempList.size(); i++) {
            ModifyDTOInSWT modifyDTOInSWT = componentTempList.get(i);
            ComponentTemp componentTemp = componentTempRepository.findById(
                modifyDTOInSWT.getComponentTempId()).get();

            if (modifyDTOInSWT.isDeleted()) {
                componentTempRepository.delete(componentTemp);
            } else {
                componentTemp.updateInSWT(modifyDTOInSWT);
            }
        }

        return findSpaceWallTemp(spaceWallId);
    }

    @Transactional
    public ResponseDto doneSpaceWallTemp(Long spaceWallId, Long memberId) {
        /*
        먼저 spaceWall, spaceWallTemp, componentList, componentTempList를 불러온다.
        컴포넌트 먼저 업데이터, 삭제
         */
        SpaceWall spaceWall = spaceWallRepository.findById(spaceWallId).get();
        SpaceWallTemp spaceWallTemp = spaceWallTempRepository.findSpaceWallTempBySpaceWallId(
            spaceWallId);

        List<Component> componentList = componentRepository.findComponentBySpaceWall(spaceWall);
        List<ComponentTemp> componentTempList = componentTempRepository.findComponentTempBySpaceWallTemp(
            spaceWallTemp);

        /*
        뭐로 for문을 돌릴까??
        component로 돌려서 temp에 id값이 들어있지 않으면 삭제를 하고
        있으면 update

        그럼 새로 생성된 거는 componentTemp 한번 돌리면서 detleted가 false이면서 compontnet_id가
        Null값인 애로 새로 생성
         */

        for (int i = 0; i < componentList.size(); i++) {
            Component component = componentList.get(i);
            boolean updated = false;

            for (int j = 0; j < componentTempList.size(); j++) {
                ComponentTemp componentTemp = componentTempList.get(j);
                if (Objects.equals(componentTemp.getComponentId(), component.getId())) {
                    component.update(componentTemp);
                    updated = true;
                }
            }

            if (!updated) {
                componentRepository.delete(component);
            }

        }

        for (int i = 0; i < componentTempList.size(); i++) {
            ComponentTemp componentTemp = componentTempList.get(i);

            if (!componentTemp.isDeleted() && componentTemp.getComponentId() == null) {
                Component newComponent = Component.builder()
                    .parentSpaceWall(spaceWall)
                    .template(componentTemp.getTemplate())
                    .thisSpaceWall(componentTemp.getThisSpaceWall())
                    .type(componentTemp.getType())
                    .visible(componentTemp.isVisible())
                    .title(componentTemp.getTitle())
                    .content(componentTemp.getContent())
                    .sequence(componentTemp.getSequence())
                    .build();
                componentRepository.save(newComponent);

                if (!componentTemp.getType().equals("temp")) {
                    continue;
                }

                templateUsedHistoryService.saveHistory(memberId,
                    componentTemp.getTemplate().getId());
            }

        }

        for (int i = 0; i < componentTempList.size(); i++) {
            componentTempRepository.delete(componentTempList.get(i));
        }

        spaceWall.updateBySWT(spaceWallTemp);

        spaceWallTempRepository.delete(spaceWallTemp);

        return spaceWallService.findById(spaceWallId);
    }

}
