package com.fastcampus.jober.global.security.auth.session;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.global.constant.Auths;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Slf4j
@RequiredArgsConstructor
public class MemberDetails implements UserDetails {

    private final Member member;
    private final Long spaceWallId;
    private final Map<Long, Auths> longAuthsMap;

    public MemberDetails(Member member) {
        this.member = member;
        this.spaceWallId = null;
        this.longAuthsMap = new HashMap<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        // spaceWallId 와 사용자 공유스페이스 id 비교해서 권한 등록
        if (longAuthsMap.get(spaceWallId) != null) {
            authorities.add(new SimpleGrantedAuthority(longAuthsMap.get(spaceWallId).name()));
        }


        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getMemberId() { return member.getId(); }
}
