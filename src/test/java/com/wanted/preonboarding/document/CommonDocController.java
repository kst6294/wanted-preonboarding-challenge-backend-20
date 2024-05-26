package com.wanted.preonboarding.document;

import com.wanted.preonboarding.auth.enums.JwtErrorEnum;
import com.wanted.preonboarding.module.common.enums.EnumType;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.common.enums.Yn;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.user.enums.MemberShip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class CommonDocController {

    @GetMapping("/enums")
    public ApiResponseDto<EnumDocs> findEnums() {

        Map<String, String> redisKey = getDocs(RedisKey.values());
        Map<String, String> yn = getDocs(Yn.values());
        Map<String, String> memberShip = getDocs(MemberShip.values());
        Map<String, String> jwtErrorEnum = getDocs(JwtErrorEnum.values());
        Map<String, String> productStatus = getDocs(ProductStatus.values());


        EnumDocs build = EnumDocs.builder()
                .redisKey(redisKey)
                .yn(yn)
                .memberShip(memberShip)
                .jwtErrorEnum(jwtErrorEnum)
                .productStatus(productStatus)
                .build();

        return ApiResponseDto.of(build);
    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getName, EnumType::getDescription));
    }
}
