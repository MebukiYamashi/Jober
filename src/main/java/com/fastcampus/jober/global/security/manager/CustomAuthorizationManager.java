package com.fastcampus.jober.global.security.manager;

import com.fastcampus.jober.domain.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.spacewall.repository.SpaceWallRepository;
import com.fastcampus.jober.domain.spacewallmember.domain.SpaceWallMember;
import com.fastcampus.jober.domain.spacewallmember.repository.SpaceWallMemberRepository;
import com.fastcampus.jober.domain.spacewallpermission.repository.SpaceWallPermissionRepository;
import com.fastcampus.jober.global.auth.session.MemberDetails;
import com.fastcampus.jober.global.constant.Auths;
import com.fastcampus.jober.global.error.exception.SpaceWallNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthorizationManager implements
    AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private SpaceWallRepository spaceWallRepository;

    @Autowired
    private SpaceWallMemberRepository spaceWallMemberRepository;
    @Autowired
    private SpaceWallPermissionRepository permissionRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
        RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();

        Long spaceWallId = parseSpaceWallId(request.getRequestURI());
        SpaceWall spaceWall = spaceWallRepository.findById(spaceWallId)
            .orElseThrow(() -> new SpaceWallNotFoundException("찾을 수 없습니다."));

        if (request.getRequestURI().contains("/spaces/view") && !spaceWall.isAuthorized()) {
            return new AuthorizationDecision(true);
        }

        MemberDetails memberDetails = (MemberDetails) authentication.get().getPrincipal();

        SpaceWallMember spaceWallMember = spaceWallMemberRepository.selectSpaceWallMember(
            spaceWallId, memberDetails.getMemberId());

        if (spaceWallMember == null) {
            return new AuthorizationDecision(false);
        }

        Auths auth = permissionRepository.selectAuths(
            spaceWallMember.getId());

        boolean access = checkSpacesUrlPattern(request, auth);

        return new AuthorizationDecision(access);
    }

    private Long parseSpaceWallId(String requestUri) {
        String[] array = requestUri.split("/");

        Long spaceWallId = Long.parseLong(array[array.length - 1]);

        return spaceWallId;
    }

    private boolean checkSpacesUrlPattern(HttpServletRequest request,
        Auths auth) {

        String uri = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        if (uri.contains("/spaces/member")) {
            return auth == Auths.OWNER;
        }

        if (uri.contains("/spaces/history")) {
            return auth == Auths.EDITOR || auth == Auths.OWNER;
        }

        if (uri.contains("/spaces")) {
            if (method == HttpMethod.DELETE) {
                return auth == Auths.OWNER;
            }

            if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                return auth == Auths.EDITOR
                    || auth == Auths.OWNER;
            }

            return true;
        }

        return false;
    }
}