package com.fastcampus.jober.domain.spacewall.repository;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.spacewall.domain.SpaceWall;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceWallRepository extends JpaRepository<SpaceWall, Long> {

    List<SpaceWall> findBySequenceBetween(int start, int end);

    boolean existsSpaceWallById(Long id);

    boolean existsByUrl(String url);

}
