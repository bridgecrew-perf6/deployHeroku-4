package com.company.util;

import com.company.dto.ProfileJwtDto;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenexception;
import com.company.exception.TokenNotVaildException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private final static String secretKey = "kalitso'z";

    public static String createJwt(Integer id, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.claim("role", role);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
        jwtBuilder.setIssuer("kun.uz");
        //   String jwt= jwtBuilder.compact();    shunday qilsa ham boladi
        return jwtBuilder.compact();
    }

    public static ProfileJwtDto deCode(String jwt) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);
        Claims claims = jws.getBody();
        String id = claims.getSubject();
        String role = (String) claims.get("role");
        return new ProfileJwtDto(Integer.parseInt(id), ProfileRole.valueOf(role));
    }
    public static Integer deCodeVerification(String jwt) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);
        Claims claims = jws.getBody();
        String id = claims.getSubject();
        return Integer.parseInt(id);
    }

    public static Integer getIdFromHeader(HttpServletRequest request,ProfileRole ... requiredRoles) {
        try {
            ProfileJwtDto dto= (ProfileJwtDto) request.getAttribute("jwtDto");
            if(requiredRoles == null || requiredRoles.length == 0){
                return dto.getId();
            }
            for (ProfileRole role : requiredRoles) {
                if(dto.getRole().equals(role)){
                    return dto.getId();
                }
            }
        } catch (RuntimeException e) {
            throw new TokenNotVaildException("not authorized");
        }

        throw new AppForbiddenexception("profile role emas");
    }

    public static ProfileJwtDto getProfileFromHeader(HttpServletRequest request,ProfileRole ... requiredRoles) {
        try {
            ProfileJwtDto dto= (ProfileJwtDto) request.getAttribute("jwtDto");
            if(requiredRoles == null || requiredRoles.length == 0){
                return dto;
            }
            for (ProfileRole role : requiredRoles) {
                if(dto.getRole().equals(role)){
                    return dto;
                }
            }
        } catch (RuntimeException e) {
            throw new TokenNotVaildException("not authorized");
        }

        throw new AppForbiddenexception("profile role emas");
    }

    public static String encode(Integer id) {
        return createJwt(id,null);
    }

    public static String encode(Integer id,ProfileRole role) {
        return createJwt(id,role);
    }
}

