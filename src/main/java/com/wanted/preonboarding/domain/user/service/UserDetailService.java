package com.wanted.preonboarding.domain.user.service;

import com.wanted.preonboarding.domain.user.entity.CustomUserDetails;
import com.wanted.preonboarding.domain.user.entity.User;
import com.wanted.preonboarding.domain.user.repository.UserRepository;
import com.wanted.preonboarding.global.exception.entity.RestApiException;
import com.wanted.preonboarding.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new RestApiException(UserErrorCode.USER_NOT_FOUND, "UserDetail 생성 중 사용자를 찾을 수 없습니다.(id="+userId+")"));
        CustomUserDetails customUserDetails = CustomUserDetails.of(user);

        return customUserDetails;
    }

}
