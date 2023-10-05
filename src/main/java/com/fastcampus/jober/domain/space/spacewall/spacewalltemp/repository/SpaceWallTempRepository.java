package com.fastcampus.jober.domain.space.spacewall.spacewalltemp.repository;

import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.domain.SpaceWallTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceWallTempRepository extends JpaRepository<SpaceWallTemp, Long> {


    @Query("SELECT CASE WHEN COUNT(swt) > 0 THEN true ELSE false END FROM SpaceWallTemp swt WHERE swt.spaceWall.id = :spaceWallId")
    boolean existsSpaceWallTempBySpaceWall(@Param("spaceWallId")Long spaceWallId);

    @Query("select SWT from SpaceWallTemp SWT where SWT.spaceWall = (select SW from SpaceWall SW where SW.id = :spaceWallId)")
    SpaceWallTemp findSpaceWallTempBySpaceWallId(@Param("spaceWallId") Long spaceWallId);


}
