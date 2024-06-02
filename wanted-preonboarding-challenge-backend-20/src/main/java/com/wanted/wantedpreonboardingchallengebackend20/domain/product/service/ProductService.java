package com.wanted.wantedpreonboardingchallengebackend20.domain.product.service;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.request.RegistProductRequestDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response.ListProductResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response.RegistProductResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;

public interface ProductService {
    RegistProductResponseDto registProduct(User user, RegistProductRequestDto requestDto);
    ListProductResponseDto listProduct();
}
