package com.example.wanted.mock;

import com.example.wanted.user.domain.User;
import com.example.wanted.user.service.port.JwtUtil;
import io.jsonwebtoken.Claims;

public class FakeJwtUtil implements JwtUtil {
    @Override
    public String createAccessToken(User user) {
        return "aasdfasdfasdfasdfasdfasdfasdf";
    }

    @Override
    public Long getUserId(String token) {
        return null;
    }

    @Override
    public String getUserRole(String token) {
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public Claims parseClaims(String accessToken) {
        return null;
    }
}
