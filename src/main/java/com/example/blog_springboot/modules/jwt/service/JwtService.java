package com.example.blog_springboot.modules.jwt.service;

import com.example.blog_springboot.modules.jwt.types.JwtDecode;
import com.example.blog_springboot.modules.user.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public interface JwtService {
    public String extractUsername(String token);

    public String generateAccessToken(UserDetails user);
    public String generateRefreshToken(UserDetails user);

    public String buildToken(Map<String,Object> extraClaims,UserDetails user,long tokenTime);

    public boolean isTokenValid(String token,UserDetails user);
}
