package com.maurilio.autorizacao.security;

import com.auth0.jwt.JWT;
import com.maurilio.autorizacao.serice.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token;

        String authorization = request.getHeader("Authorization");

        if(authorization != null){
            token = authorization.replace("Bearer ", "");

            String subject = this.tokenService.getSubject(token);

            UserData user = new UserData("maurilio", subject, "$2a$10$G92VyvGNP9dLZ0BlbEmgCeBoE6r9yWjGiAYAEx9A3KU91SaRO74mS", "ADMIN");

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }


}
