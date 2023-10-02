package com.fastcampus.jober.domain.spacewalltemp.repository;

import com.fastcampus.jober.domain.spacewalltemp.domain.SpaceWallTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceWallTempRepository extends JpaRepository<SpaceWallTemp, Long> {

}
