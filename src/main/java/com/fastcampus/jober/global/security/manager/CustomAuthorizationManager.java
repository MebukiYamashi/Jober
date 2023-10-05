package com.fastcampus.jober.global.security.manager;

import com.fastcampus.jober.domain.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.spacewall.repository.SpaceWallRepository;
import com.fastcampus.jober.domain.spacewallmember.domain.SpaceWallMember;
import com.fastcampus.jober.domain.spacewallmember.repository.SpaceWallMemberRepository;
import com.fastcampus.jober.domain.spacewallpermission.repository.SpaceWallPermissionRepository;
import com.fastcampus.jober.global.auth.session.MemberDetails;
import com.fastcampus.jober.global.constant.Auths;
import com.fastcampus.jober.global.error.exception.SpaceWallNotFoundException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.function.Supplier;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
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

        MemberDetails memberDetails = (MemberDetails) authentication.get().getPrincipal();

        boolean access = checkUrlPattern(request, memberDetails.getMemberId());

        return new AuthorizationDecision(access);
    }

    private Long parseSpaceWallId(String requestUri) throws NumberFormatException {
        String[] array = requestUri.split("/");

        Long spaceWallId = Long.parseLong(array[array.length - 1]);

        return spaceWallId;
    }

    private boolean checkUrlPattern(HttpServletRequest request,
        Long memberId) {

        String uri = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        if (uri.contains("/new-spaces")) {
            return checkNewSpacesUrlPattern(request, memberId);
        }

        Long spaceWallId = parseSpaceWallId(uri);
        SpaceWall spaceWall = spaceWallRepository.findById(spaceWallId)
            .orElseThrow(() -> new SpaceWallNotFoundException("찾을 수 없습니다."));

        if (uri.contains("/spaces/view") && !spaceWall.isAuthorized()) {
            return true;
        }

        SpaceWallMember spaceWallMember = spaceWallMemberRepository.selectSpaceWallMember(
            spaceWallId, memberId);

        if (spaceWallMember == null) {
            return false;
        }

        Auths auth = permissionRepository.selectAuths(
            spaceWallMember.getId());

        if (uri.contains("/spaces")) {
            return checkSpacesUrlPattern(uri, method, auth);
        }

        if (uri.contains("Temps")) {
            return checkTempUrlPattern(uri, method, auth);
        }

        return false;
    }

    private boolean checkNewSpacesUrlPattern(HttpServletRequest request, Long memberId) {
        JSONParser jsonParser = null;
        try {
            jsonParser = new JSONParser(request.getInputStream());
            LinkedHashMap<String, Object> jsonObject = jsonParser.parseObject();

            if (StringUtils.isBlank(jsonObject.get("parentSpaceWallId").toString())) {
                return true;
            }

            Long parentSpaceWallId = Long.parseLong(
                jsonObject.get("parentSpaceWallId").toString());

            SpaceWallMember spaceWallMember = spaceWallMemberRepository.selectSpaceWallMember(
                parentSpaceWallId, memberId);

            if (spaceWallMember == null) {
                return false;
            }

            Auths auth = permissionRepository.selectAuths(
                spaceWallMember.getId());

            return auth == Auths.EDITOR || auth == Auths.OWNER;
        } catch (IOException | ParseException e) {
            return false;
        }
    }

    private boolean checkSpacesUrlPattern(String uri, HttpMethod method, Auths auth) {

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

    private boolean checkTempUrlPattern(String uri, HttpMethod method, Auths auth) {
        if (uri.contains("/spaceTemps") || uri.contains("/componentTemps")) {
            return auth == Auths.EDITOR || auth == Auths.OWNER;
        }

        return false;
    }
}