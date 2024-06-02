package com.wanted.wantedpreonboardingchallengebackend20.domain.product.service;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.request.RegistProductRequestDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response.ListProductResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response.RegistProductResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.Product;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.ProductState;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.repository.ProductRepository;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    @Transactional(readOnly = true)
    public ListProductResponseDto listProduct(){
        List<Product> productList=productRepository.findAll();
        List<ListProductResponseDto.ProductDto> response=new ArrayList<>();
        for(int i=0;i<productList.size();i++){
            Product now=productList.get(i);
            response.add(ListProductResponseDto.ProductDto.builder()
                    .productName(now.getName())
                    .state(now.getState())
                    .price(now.getPrice())
                    .quantity(now.getQuantity())
                    .seller(ListProductResponseDto.UserDto.builder().userName(now.getUser().getName()).build())
                    .build());
        }
        return ListProductResponseDto.builder()
                .productDtoList(response).build();
    }
    @Override
    @Transactional
    public boolean buyProduct(User user,Long productId){
        Product product=productRepository.findById(productId).get();
        User foundUser=userRepository.findByNameAndUserId(user.getName(), user.getUserId());
        if(product.getState()==ProductState.AVAILABLE){
            productRepository.modifyProductState(productId);
            return true;
        }
        return false;
    }
}
