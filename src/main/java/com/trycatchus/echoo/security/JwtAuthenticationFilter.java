package com.trycatchus.echoo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.trycatchus.echoo.dto.responses.auth.UserDecodedResponse;
import com.trycatchus.echoo.exception.JwtAuthenticationException;
import com.trycatchus.echoo.service.Auth0JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final Auth0JwtService jwtService;

    public JwtAuthenticationFilter(Auth0JwtService jwtService, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        Boolean isAuthPath = path.startsWith("/api/v1/auth");
        Boolean isHealthPath = path.startsWith("/health");
        Boolean isOptions = "OPTIONS".equals(request.getMethod());
        
        Boolean shouldSkip = isAuthPath || isHealthPath || isOptions;
        
        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            var decodedJWT = jwtService.decodeToken(authHeader);

            String userId = decodedJWT.getClaim("userId").asString();
            String role = decodedJWT.getClaim("role").asString();
            String firstName = decodedJWT.getClaim("firstName").asString();
            String username = decodedJWT.getClaim("username").asString();

            UserDecodedResponse principal = new UserDecodedResponse(userId, firstName, username, role);

            var auth = new UsernamePasswordAuthenticationToken(
                principal, null, java.util.List.of(() -> "ROLE_" + role));

            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
    
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
    
            String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Invalid token";
            JwtAuthenticationException authEx = new JwtAuthenticationException(errorMessage);

            authenticationEntryPoint.commence(request, response, authEx);

        }
    }
}