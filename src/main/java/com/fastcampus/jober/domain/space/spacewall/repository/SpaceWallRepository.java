package com.fastcampus.jober.domain.space.spacewall.repository;

import com.fastcampus.jober.domain.space.spacewall.domain.SpaceWall;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceWallRepository extends JpaRepository<SpaceWall, Long> {

    List<SpaceWall> findBySequenceBetween(int start, int end);

    boolean existsSpaceWallById(Long id);

    boolean existsByUrl(String url);

}
