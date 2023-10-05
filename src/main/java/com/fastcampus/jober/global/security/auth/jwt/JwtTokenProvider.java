package com.fastcampus.jober.global.security.auth.jwt;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.global.constant.Auths;
import com.fastcampus.jober.global.error.exception.TokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.io.IOException;
import java.util.*;

import static com.fastcampus.jober.domain.member.dto.MemberResponse.PermissionMappedDTO;
import static com.fastcampus.jober.global.constant.ErrorCode.*;

@Slf4j
@Component
public class JwtTokenProvider {

    public static final Long EXP = 1000L * 60 * 60 * 24; // 1시간
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함
    public static final String HEADER = "Authorization";
    public static String SECRET;

    @Value("${my-env.jwt.secret}")
    public void setSECRET(String secret) {
        SECRET = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public static Long getMemberIdFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
            .getBody().get("id", Long.class);
    }

    public static Map<Long, Auths> getAssignedSpaceWallMemberIdsFromToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String jsonList = (String) claims.get("assignedSpaceMemberInfos");

        return convertJsonToObjectList(jsonList);
    }

    public static String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
            .getBody().get("email", String.class);
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException e) {
            throw new TokenException(INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new TokenException(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new TokenException(UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new TokenException(ILLEGAL_ARGUMENT);
        } catch (MalformedJwtException e) {
            throw new TokenException(MALFORMED_TOKEN);
        } catch (BadCredentialsException | AuthenticationCredentialsNotFoundException e) {
            throw new TokenException(NOT_FOUNT_JWT);
        } catch (Exception e) {
            throw new TokenException(UNKNOWN_TOKEN_ERROR);
        }
    }

    public static boolean isExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static String create(Member member, List<PermissionMappedDTO> permissionDTOs) {

        Map<Long, Auths> spaceWallMemberInfos = new HashMap<>();
        for (PermissionMappedDTO permissionMappedDTO : permissionDTOs) {
            spaceWallMemberInfos.put(permissionMappedDTO.getSpaceWallId(), permissionMappedDTO.getAuths());
        }

        Claims claims = Jwts.claims();
        claims.put("id", member.getId());
        claims.put("email", member.getEmail());
        claims.put("assignedSpaceMemberInfos", convertObjectListToJson(spaceWallMemberInfos));

        String jwt = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXP))
            .signWith(SignatureAlgorithm.HS256, SECRET)
            .compact();

        return TOKEN_PREFIX + jwt;
    }

    private static String convertObjectListToJson(Map<Long, Auths> objectList) {
        // ObjectMapper를 사용하여 객체 리스트를 JSON으로 직렬화
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(objectList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<Long, Auths> convertJsonToObjectList(String jsonList) {
        // ObjectMapper를 사용하여 JSON 문자열을 객체 리스트로 역직렬화
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonList, new TypeReference<Map<Long, Auths>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void delete(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        claims.setExpiration(new Date(0));
        Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
