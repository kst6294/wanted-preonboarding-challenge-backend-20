package com.wanted.wantedpreonboardingchallengebackend20.domain.user.service;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.Product;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.request.UserRequestDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response.SellingProductListResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response.UserResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User signUp(UserRequestDto requestDto) {
        User user=User.builder()
                .userId(requestDto.getUserId())
                .name(requestDto.getName())
                .build();
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User signIn(UserRequestDto requestDto){
        return userRepository.findByNameAndUserId(requestDto.getName(), requestDto.getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public SellingProductListResponseDto sellingProductList(User user){
        User foundUser=userRepository.findByNameAndUserId(user.getName(),user.getUserId());
        List<Product> productList=foundUser.getProductList();
        List<SellingProductListResponseDto.ProductResponseDto> responseDto=new ArrayList<>();
        for(int i=0;i<productList.size();i++){
            Product now=productList.get(i);
            responseDto.add(SellingProductListResponseDto.ProductResponseDto.builder()
                    .productName(now.getName())
                    .state(now.getState())
                    .price(now.getPrice())
                    .quantity(now.getQuantity())
                    .build());
        }
        return SellingProductListResponseDto.builder()
                .productList(responseDto).build();
    }


}
