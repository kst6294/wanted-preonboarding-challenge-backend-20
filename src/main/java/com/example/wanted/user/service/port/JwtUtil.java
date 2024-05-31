package com.example.wanted.user.service.port;

import com.example.wanted.user.domain.User;
import io.jsonwebtoken.Claims;

public interface JwtUtil {

    String createAccessToken(User user);
    Long getUserId(String token);
    String getUserRole(String token);
    boolean validateToken(String token);
    Claims parseClaims(String accessToken);
}
