package com.wanted.wantedpreonboardingchallengebackend20.domain.product.service;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.request.RegistProductRequestDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response.RegistProductResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.Product;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.ProductState;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.repository.ProductRepository;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public RegistProductResponseDto registProduct(User user, RegistProductRequestDto requestDto){
        User foundUser=userRepository.findByNameAndUserId(user.getName(),user.getUserId());
        Product product=Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .quantity(requestDto.getQuantity())
                .state(ProductState.AVAILABLE)
                .build();
        productRepository.save(product);
        product.assignUser(foundUser);
        return RegistProductResponseDto.builder()
                .name(product.getName())
                .price(product.getQuantity())
                .quantity(product.getQuantity())
                .user(RegistProductResponseDto.UserResponseDto.builder().
                        userId(user.getUserId()).build())
                .build();
    }
}
