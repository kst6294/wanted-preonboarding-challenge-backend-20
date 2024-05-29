package com.wanted.preonboarding.document;

import com.wanted.preonboarding.auth.dto.CreateAuthToken;
import com.wanted.preonboarding.auth.enums.JwtErrorEnum;
import com.wanted.preonboarding.module.common.enums.EnumType;
import com.wanted.preonboarding.module.common.enums.OrderType;
import com.wanted.preonboarding.module.common.enums.RedisKey;
import com.wanted.preonboarding.module.common.enums.Yn;
import com.wanted.preonboarding.module.exception.user.NotFoundUserException;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.order.enums.UserRole;
import com.wanted.preonboarding.module.product.enums.ProductStatus;
import com.wanted.preonboarding.module.user.enums.MemberShip;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        Map<String, String> orderType = getDocs(OrderType.values());
        Map<String, String> orderStatus = getDocs(OrderStatus.values());
        Map<String, String> userRole = getDocs(UserRole.values());


        EnumDocs build = EnumDocs.builder()
                .redisKey(redisKey)
                .yn(yn)
                .memberShip(memberShip)
                .jwtErrorEnum(jwtErrorEnum)
                .productStatus(productStatus)
                .orderType(orderType)
                .orderStatus(orderStatus)
                .userRole(userRole)
                .build();

        return ApiResponseDto.of(build);
    }


    @PostMapping("/error")
    public void errorSample(@RequestBody @Validated SampleRequest sampleRequest) {
    }


    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getName, EnumType::getDescription));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SampleRequest {

        @NotEmpty
        private String name;

        @Email
        private String email;
    }

}
