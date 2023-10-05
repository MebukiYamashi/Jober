package com.fastcampus.jober.domain.spacewallmember.service;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.member.repository.MemberRepository;
import com.fastcampus.jober.domain.spacewallmember.domain.SpaceWallMember;
import com.fastcampus.jober.domain.spacewallmember.dto.SpaceWallMemberRequest;
import com.fastcampus.jober.domain.spacewallmember.dto.SpaceWallMemberResponse;
import com.fastcampus.jober.domain.spacewallmember.repository.SpaceWallMemberRepository;
import com.fastcampus.jober.domain.spacewallpermission.repository.SpaceWallPermissionRepository;
import com.fastcampus.jober.global.auth.jwt.JwtTokenProvider;
import com.fastcampus.jober.global.auth.session.MemberDetails;
import com.fastcampus.jober.global.error.exception.SpaceWallMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fastcampus.jober.domain.spacewallmember.dto.SpaceWallMemberRequest.*;
import static com.fastcampus.jober.domain.spacewallmember.dto.SpaceWallMemberResponse.*;
import static com.fastcampus.jober.global.constant.ErrorCode.NOT_ALLOWED_DELETE_SPACE_WALL_MEMBER;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceWallMemberService {

    private final SpaceWallMemberRepository spaceWallMemberRepository;
    private final SpaceWallPermissionRepository spaceWallPermissionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveSpaceWallMember(
            Long spaceWallId,
            List<AssignDTO> requests
    ) {
        // 공동 작업자로 등록된 이메일인지 체크
        for (AssignDTO request : requests) {
            SpaceWallMember spaceWallMember = spaceWallMemberRepository
                    .findSpaceWallMemberBySpaceWallIdAndEmail(spaceWallId, request.getEmail());

            // 공동 작업자로 등록되어 있지 않은 경우
            if (spaceWallMember == null) {
                Long memberId = memberRepository.findByEmail(request.getEmail()).get().getId();
                spaceWallMemberRepository.insertMember(memberId, spaceWallId); // 공유스페이스 멤버 등록
                spaceWallPermissionRepository.insertPermission( // 공유스페이스 멤버 권한 추가
                        spaceWallMemberRepository.selectSpaceWallMember(spaceWallId, memberId).getId(),
                        request.getAuths()
                );
                continue;
            }

            // 공동 작업자로 이미 등록되어 있는 경우 권한만 수정
            spaceWallPermissionRepository.updatePermission(spaceWallMember.getId(), request.getAuths());
        }

        // 요청 데이터와 DB에 저장된 데이터가 다를 경우, 차이나는 공동 작업자 삭제
        List<String> emails = findAllEmailsNotInRequest(spaceWallId, requests);

        MemberDetails memberDetails =
                (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentMemberId = memberDetails.getMemberId();
        Optional<Member> currentMember = memberRepository.findById(currentMemberId);
        String ownEmail = null;
        if (currentMember.isPresent()) {
            ownEmail= currentMember.get().getEmail();
        }

        String creatorEmail = memberRepository.findCreateMemberBySpaceWallId(spaceWallId).getEmail();

        for (String email : emails) { // 자기 자신이거나 생성자 OWNER 인경우
            if (email.equals(creatorEmail) || email.equals(ownEmail))
                throw new SpaceWallMemberException(NOT_ALLOWED_DELETE_SPACE_WALL_MEMBER);
        }
        removeAllSpaceWallMembers(spaceWallId, emails);
    }

    @Transactional
    public List<SpaceWallMemberDTO> findSpaceWallMember(Long spaceWallId) {
        List<SpaceWallMemberDTO> response = new ArrayList<>();

        List<SpaceWallMember> spaceWallMemberList = spaceWallMemberRepository.selectAllSpaceWallMembers(spaceWallId);
        for (SpaceWallMember spaceWallMember : spaceWallMemberList) {

            response.add(
                    SpaceWallMemberDTO.builder()
                            .id(spaceWallMember.getId())
                            .email(spaceWallMember.getEmail())
                            .username(spaceWallMember.getUsername())
                            .auths(spaceWallPermissionRepository.selectAuths(spaceWallMember.getId()))
                            .build()
            );
        }
        return response;
    }

    // SpaceWallMemberService.saveSpaceWallMember()에서 사용합니다.
    public void removeAllSpaceWallMembers(Long spaceWallId, List<String> emails) {
        for (String email : emails) {
            spaceWallMemberRepository.deleteAllSpaceWallMemberByEmail(spaceWallId, email);
        }
    }

    // SpaceWallMemberService.saveSpaceWallMember()에서 사용합니다.
    public List<String> findAllEmailsNotInRequest(
            Long spaceWallId,
            List<AssignDTO> requests
    ) {
        List<SpaceWallMember> spaceWallMembers = spaceWallMemberRepository.selectAllSpaceWallMembers(spaceWallId);
        List<String> emails = new ArrayList<>();

        // 권한 삭제 -> 공동작업자 멤버 삭제
        for (SpaceWallMember spaceWallMember : spaceWallMembers) { // DB데이터 리스트
            String emailInDB = spaceWallMember.getEmail(); // DB 공동작업자 이메일
            int count = 0;
            for (AssignDTO request : requests) { // 요청데이터 리스트
                if (request.getEmail().equals(emailInDB)) break;
                count++;
            }
            if (count == requests.size() ) emails.add(emailInDB);

        }
        return emails;
    }
}
