package com.wanted.wantedpreonboardingchallengebackend20.domain.user.service;

import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.request.UserRequestDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response.SellingProductListResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;

import java.util.List;

public interface UserService {
    User signUp(UserRequestDto requestDto);
    User signIn(UserRequestDto requestDto);
    SellingProductListResponseDto sellingProductList(User user);
}
