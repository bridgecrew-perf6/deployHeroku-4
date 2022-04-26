package com.company.config;

import com.company.dto.ProfileJwtDto;
import com.company.enums.ProfileRole;
import com.company.util.JwtUtil;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final  String authHeadr = request.getHeader("Authorization");

        if(authHeadr == null || authHeadr.isEmpty() || !authHeadr.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Message","Token qani mazgi");
            return;
        }

        try {
            String[] jwt = authHeadr.split(" ");
            if(jwt.length != 2){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            ProfileJwtDto jwtDto = JwtUtil.deCode(jwt[1]);
            request.setAttribute("jwtDto",jwtDto);
        }catch (JwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request,response);
    }
}
