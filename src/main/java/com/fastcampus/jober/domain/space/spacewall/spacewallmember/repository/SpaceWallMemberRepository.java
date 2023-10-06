package com.fastcampus.jober.domain.space.spacewall.spacewallmember.repository;

import com.fastcampus.jober.domain.space.spacewall.spacewallmember.domain.SpaceWallMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpaceWallMemberRepository extends JpaRepository<SpaceWallMember, Long> {

    Optional<SpaceWallMember> findBySpaceWallId(Long spaceWallId);
    @Query("SELECT swm FROM SpaceWallMember swm WHERE swm.spaceWall.id = :spaceWallId AND swm.member.id = :memberId")
    SpaceWallMember selectSpaceWallMember(@Param("spaceWallId") Long spaceWallId, @Param("memberId") Long memberId);

    @Modifying
    @Query(
            value = "INSERT INTO space_wall_member (member_id, space_wall_id, created_at) " +
                    "VALUES (:memberId, :spaceWallId, now())",
            nativeQuery = true
    )
    void insertMember(@Param("memberId") Long memberId, @Param("spaceWallId") Long spaceWallId);

    @Query("SELECT swm FROM SpaceWallMember swm WHERE swm.spaceWall.id = :spaceWallId")
    List<SpaceWallMember> selectAllSpaceWallMembers(@Param("spaceWallId") Long spaceWallId);

    @Modifying
    @Query(
            value = "DELETE FROM space_wall_member WHERE id = " +
                    "(SELECT * FROM " +
                        "(SELECT swm.id FROM space_wall_member swm " +
                        "INNER JOIN member m ON m.id = swm.member_id " +
                        "WHERE swm.space_wall_id = :spaceWallId AND m.email = :email) AS t)",
            nativeQuery = true
    )
    void deleteAllSpaceWallMemberByEmail(@Param("spaceWallId") Long spaceWallId, @Param("email") String email);

    @Query("SELECT swm FROM SpaceWallMember swm WHERE swm.spaceWall.id = :spaceWallId AND swm.member.email = :email")
    SpaceWallMember findSpaceWallMemberBySpaceWallIdAndEmail(@Param("spaceWallId") Long spaceWallId, @Param("email") String email);
}
