package com.example.blog_springboot.configs;

import com.example.blog_springboot.modules.jwt.service.JwtService;
import com.example.blog_springboot.modules.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    final JwtService jwtService;
    final UserService userService;
    final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService,UserService userService,UserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    )throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        String userName = jwtService.extractUsername(jwt);

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = userDetailsService.loadUserByUsername(userName);

            if(jwtService.isTokenValid(jwt,user)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                  null,
                        user.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
