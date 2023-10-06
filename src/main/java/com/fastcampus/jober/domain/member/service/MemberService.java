package com.fastcampus.jober.domain.member.service;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.member.dto.MemberRequest;
import com.fastcampus.jober.domain.member.dto.MemberResponse.JoinDTO;
import com.fastcampus.jober.domain.member.dto.MemberResponse.MemberDTO;
import com.fastcampus.jober.domain.member.repository.MemberRepository;
import com.fastcampus.jober.domain.space.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.space.spacewall.spacewallmember.domain.SpaceWallMember;
import com.fastcampus.jober.global.security.auth.jwt.JwtTokenProvider;
import com.fastcampus.jober.global.security.auth.session.MemberDetails;
import com.fastcampus.jober.global.error.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fastcampus.jober.domain.member.dto.MemberResponse.MySpaceWallDTO;
import static com.fastcampus.jober.global.constant.ErrorCode.CHECK_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final AuthenticationManager authenticationManager;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Map<String, Object> login(String email, String password) {
        Member member = memberRepository.findByEmail(email).
            orElseThrow(() -> new MemberException(CHECK_ID));

//        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
//            throw new Exception401(CHECK_PASSWORD.getMessage());
//        }

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        Member loginUser = memberDetails.getMember();

        Map<String, Object> response = new HashMap<>();
        response.put("token", JwtTokenProvider.create(loginUser));
        response.put("memberInfo", new MemberDTO(loginUser));

        return response;
    }

    @Transactional
    public void logout(String token) {
        JwtTokenProvider.delete(token);
    }

    @Transactional
    public JoinDTO join(MemberRequest.JoinDTO request) {

//        joinRequestDTO.setPassword(bCryptPasswordEncoder.encode(joinRequestDTO.getPassword()));

        Member userPS = memberRepository.save(request.toEntity());

        JoinDTO response = new JoinDTO(userPS);
        response.setUsername(response.getUsername());
        response.setEmail(response.getEmail());

        return response;

    }

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        return memberRepository.existsMemberByEmail(email);
    }

    // 이 메서드의 목적은 사용자가 공동작업자로 속해있는 공유스페이스 목록을 조회하되,
    // 만약 공유스페이스 셋에 속한 경우 그중 가장 상위의 공유스페이스 목록을 조회함.
    @Transactional
    public List<MySpaceWallDTO> findMySpaceWalls() {

        MemberDetails memberDetails =
                (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentMemberId = memberDetails.getMemberId();

        List<MySpaceWallDTO> response = new ArrayList<>();
        List<Long> checkedSpaceWallIdList = new ArrayList<>();

        for (SpaceWallMember spaceWallMember : memberRepository.selectMySpaceWallsById(currentMemberId)) {
            SpaceWall mySpaceWall = spaceWallMember.getSpaceWall();
            Long parsedParentSpaceWallId = mySpaceWall.getParentSpaceWallId();

            // 하위 공유스페이스인 경우
            // 내가 속한 공유스페이스 id 모음에서 현재 대상 공유스페이스의 상위페이지 id를 검색함
            // 이미 있는 경우 현재 공유스페이스 id를 모음에 넣고, 로직 건너뜀(공유스페이스 조회 목록에 추가하지 않음)
            if (parsedParentSpaceWallId != null) {
                int count = 0;
                for (Long Id : checkedSpaceWallIdList) {
                    if (parsedParentSpaceWallId.equals(Id)) {
                        count++;
                    }
                }
                if (count != 0) {
                    checkedSpaceWallIdList.add(mySpaceWall.getId());
                    continue;
                }
            }
            // 최상위 공유스페이스인 경우
            checkedSpaceWallIdList.add(mySpaceWall.getId());
            MySpaceWallDTO mySpaceWallsDTO =
                    new MySpaceWallDTO(
                            mySpaceWall.getId(),
                            mySpaceWall.getTitle(),
                            spaceWallMember.getAuths()
                    );
            response.add(mySpaceWallsDTO);
        }
        return response;
    }
}
