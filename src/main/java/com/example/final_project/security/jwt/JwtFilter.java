package com.example.final_project.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
    private static final String AUTHORIZATION = "Authorization";
    private final JwtProvider jwtProvider;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if(token != null) {
            token = token.replace("\"", "");
            if(jwtProvider.validateAccessToken(token)) {
                final Claims claims = jwtProvider.getAccessTokenClaims(token);
                final JwtAuthentication tokenInfo = JwtUtils.generate(claims);
                tokenInfo.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(tokenInfo);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
