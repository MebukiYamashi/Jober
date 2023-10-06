package com.fastcampus.jober.domain.member.repository;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.space.spacewall.spacewallmember.domain.SpaceWallMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);

    boolean existsMemberByEmail(String email);

    @Query("SELECT m.spaceWallMember FROM Member m WHERE m.id = :memberId")
    List<SpaceWallMember> selectMySpaceWallsById(@Param("memberId") Long memberId);

    @Query("SELECT sw.createMember FROM SpaceWall sw WHERE sw.id = :spaceWallId")
    Member findCreateMemberBySpaceWallId(@Param("spaceWallId") Long spaceWallId);
}
