package com.fastcampus.jober.domain.space.spacewall.spacewallpermission.service;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.member.repository.MemberRepository;
import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.repository.SpaceWallPermissionRepository;
import com.fastcampus.jober.domain.space.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.space.spacewall.repository.SpaceWallRepository;
import com.fastcampus.jober.domain.space.spacewall.spacewallmember.domain.SpaceWallMember;
import com.fastcampus.jober.domain.space.spacewall.spacewallmember.repository.SpaceWallMemberRepository;
import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.domain.SpaceWallPermission;
import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.dto.SpaceWallPermissionRequest;
import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.dto.SpaceWallPermissionResponse;
import com.fastcampus.jober.global.security.auth.session.MemberDetails;
import com.fastcampus.jober.global.constant.Auths;
import com.fastcampus.jober.global.error.exception.InvalidTargetSequenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceWallPermissionService {

    private final SpaceWallPermissionRepository spaceWallPermissionRepository;
    private final SpaceWallMemberRepository spaceWallMemberRepository;
    private final SpaceWallRepository spaceWallRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SpaceWallPermissionResponse updatePermission(Long id, SpaceWallPermissionRequest requestDto, MemberDetails memberDetails) {
        if (memberDetails == null || !memberDetails.getMember().getId().equals(requestDto.getMemberId())) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        SpaceWallPermission spaceWallPermission = spaceWallPermissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 공유페이스 권한 ID입니다."));

        spaceWallPermission.setAuths(requestDto.getAuths());

        SpaceWallPermission updatedPermission = spaceWallPermissionRepository.save(spaceWallPermission);
        return new SpaceWallPermissionResponse(updatedPermission);
    }

    @Transactional
    public SpaceWallPermissionResponse moveSpaceWallPermission(Long id, Long targetSequence, MemberDetails memberDetails) {
        if (memberDetails == null) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        SpaceWall currentSpaceWall = spaceWallRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 공유페이스 ID입니다."));

        if (!currentSpaceWall.getCreateMember().getId().equals(memberDetails.getMember().getId())) {
            throw new IllegalArgumentException("이 페이지를 이동시킬 권한이 없습니다.");
        }

        if (targetSequence == null || targetSequence < 1 || targetSequence > spaceWallRepository.count()) {
            throw new InvalidTargetSequenceException("잘못된 타겟 순서입니다. 입력 값: " + targetSequence);
        }

        int currentSequence = currentSpaceWall.getSequence();
        int targetSeqInt = targetSequence.intValue();

        if (targetSeqInt > currentSequence) {
            List<SpaceWall> affectedSpaceWalls = spaceWallRepository.findBySequenceBetween(currentSequence + 1, targetSeqInt);
            for (SpaceWall spaceWall : affectedSpaceWalls) {
                spaceWall.setSequence(spaceWall.getSequence() - 1);
                spaceWallRepository.save(spaceWall);
            }
        } else if (targetSeqInt < currentSequence) {
            List<SpaceWall> affectedSpaceWalls = spaceWallRepository.findBySequenceBetween(targetSeqInt, currentSequence - 1);
            for (SpaceWall spaceWall : affectedSpaceWalls) {
                spaceWall.setSequence(spaceWall.getSequence() + 1);
                spaceWallRepository.save(spaceWall);
            }
        } else {
            throw new IllegalArgumentException("이동할 필요가 없습니다.");
        }

        currentSpaceWall.setSequence(targetSeqInt);
        spaceWallRepository.save(currentSpaceWall);

        SpaceWallPermission spaceWallPermission = spaceWallPermissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 공유페이스 권한 ID입니다."));

        return new SpaceWallPermissionResponse(spaceWallPermission);
    }

    @Transactional
    public void assignPermissionToSpaceWall(Auths auth, Long spaceWallId, Long memberId) {
        SpaceWall spaceWall = spaceWallRepository.findById(spaceWallId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 공유페이스 ID입니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 회원 ID입니다."));

        SpaceWallMember spaceWallMember = spaceWallMemberRepository
                .findBySpaceWallId(spaceWallId)
                .orElseGet(() -> {
                    SpaceWallMember newMember = new SpaceWallMember();
                    newMember.setSpaceWall(spaceWall);
                    newMember.setMember(member);
                    return spaceWallMemberRepository.saveAndFlush(newMember);
                });

        SpaceWallPermission permission = new SpaceWallPermission();
        permission.setSpaceWallMember(spaceWallMember);
        permission.setAuths(auth);
        spaceWallPermissionRepository.save(permission);
    }
}
