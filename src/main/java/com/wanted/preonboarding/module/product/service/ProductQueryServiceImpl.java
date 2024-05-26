package com.wanted.preonboarding.module.product.service;


import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.service.UserFindService;
import com.wanted.preonboarding.module.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductQueryServiceImpl implements ProductQueryService{

    private final UserFindService userFindService;

    @Override
    public void createProduct(CreateProduct createProduct) {
        String email = SecurityUtils.currentUserEmail();
        BaseUserInfo baseUserInfo = userFindService.fetchUserInfo(email);



    }
}
