package com.example.blog_springboot.configs;

import com.example.blog_springboot.commons.ErrorResponse;
import com.example.blog_springboot.modules.jwt.service.JwtService;
import com.example.blog_springboot.modules.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

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
        try{
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
        }catch(ExpiredJwtException ex){
            sendErrorFilter(response,HttpStatus.UNAUTHORIZED,"Phiên đăng nhập đã hết hạn");
        }catch(AccessDeniedException ex){
            sendErrorFilter(response,HttpStatus.FORBIDDEN,"Không có quyền");
        }
    }

    private void sendErrorFilter(@NonNull HttpServletResponse response, HttpStatus httpStatus,String message) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = new ErrorResponse(httpStatus,httpStatus.value(),message);

        String errorEncode = new ObjectMapper().writeValueAsString(errorResponse);

        PrintWriter out = response.getWriter();
        out.print(errorEncode);
        out.flush();
    }
}
