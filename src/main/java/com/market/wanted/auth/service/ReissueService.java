package com.market.wanted.auth.service;

import com.market.wanted.auth.entity.RefreshEntity;
import com.market.wanted.auth.repository.RefreshRepository;
import com.market.wanted.auth.security.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }
        try {

            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        boolean isExist = refreshRepository.existsByRefreshToken(refresh);
        if (!isExist) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }


        String username = jwtUtil.getUsername(refresh);

        String newAccessToken = jwtUtil.createJwt("access", username, 600000L);
        String newRefreshToken = jwtUtil.createJwt("refresh", username, 86400000L);
        refreshRepository.deleteByRefreshToken(refresh);
        addRefreshEntity(username, newRefreshToken, 86400000L);

        response.setHeader("Authorization","Bearer " + newAccessToken);
        response.addCookie(createCookie("refreshToken",newRefreshToken));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    private void addRefreshEntity(String username, String refreshToken, Long expired) {
        Date date = new Date(System.currentTimeMillis() + expired);
        RefreshEntity refreshEntity = RefreshEntity
                .builder().username(username)
                .expiration(date.toString())
                .refreshToken(refreshToken)
                .build();
        refreshRepository.save(refreshEntity);
    }


    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
