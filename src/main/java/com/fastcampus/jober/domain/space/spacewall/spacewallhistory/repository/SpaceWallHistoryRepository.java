package com.fastcampus.jober.domain.space.spacewall.spacewallhistory.repository;

import com.fastcampus.jober.domain.space.spacewall.spacewallhistory.domain.SpaceWallHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceWallHistoryRepository extends JpaRepository<SpaceWallHistory, Long> {
    List<SpaceWallHistory> findTop5ByCreateMemberIdOrderByCreatedAtDesc(Long memberId);

}
