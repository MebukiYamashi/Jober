package com.fastcampus.jober.domain.componentTemp.repository;

import com.fastcampus.jober.domain.component.domain.Component;
import com.fastcampus.jober.domain.componentTemp.domain.ComponentTemp;
import com.fastcampus.jober.domain.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.spacewalltemp.domain.SpaceWallTemp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComponentTempRepository extends JpaRepository<ComponentTemp, Long> {

    boolean existsComponentTempById(Long id);

    @Query("select c from ComponentTemp c where c.parentSpaceWallTemp = :spaceWallTemp")
    List<ComponentTemp> findComponentTempBySpaceWallTemp(@Param("spaceWallTemp") SpaceWallTemp spaceWallTemp);

}
