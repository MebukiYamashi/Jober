package com.fastcampus.jober.domain.space.component.componenthistory.service;

import com.fastcampus.jober.domain.space.component.componenthistory.repository.ComponentHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComponentHistoryService {

    private final ComponentHistoryRepository componentHistoryRepository;

}
