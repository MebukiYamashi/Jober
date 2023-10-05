package com.fastcampus.jober.domain.space.component.componenthistory.repository;

import com.fastcampus.jober.domain.space.component.componenthistory.domain.ComponentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentHistoryRepository extends JpaRepository<ComponentHistory, Long> {
}
