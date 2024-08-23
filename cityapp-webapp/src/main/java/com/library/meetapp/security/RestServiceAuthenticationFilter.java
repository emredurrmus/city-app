package com.library.meetapp.security;

import java.io.IOException;
import java.util.Arrays;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RestServiceAuthenticationFilter extends OncePerRequestFilter{


    @Value("${iot.token}")
    private String iotToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String xAuth = request.getHeader("X-Authorization");

        // validate the value in xAuth
        if (xAuth != null && xAuth.equals(iotToken)) {
            Authentication token = new PreAuthenticatedAuthenticationToken("IOT", "", Arrays.asList(new SimpleGrantedAuthority("User")));
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }

}
