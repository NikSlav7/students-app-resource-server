package com.example.ResourceServer.security;

import com.example.ResourceServer.exceptions.BadTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.Collections;

public class TokenFilter extends OncePerRequestFilter {

    private final AuthManager authManager;

    public TokenFilter(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null || token.trim().isBlank()){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String id = authManager.checkToken(token);
            List<GrantedAuthority> list = new ArrayList<>();
            Authentication authentication = new UsernamePasswordAuthenticationToken(id, null, list);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (BadTokenException exception){
            filterChain.doFilter(request, response);
        }
    }
}
