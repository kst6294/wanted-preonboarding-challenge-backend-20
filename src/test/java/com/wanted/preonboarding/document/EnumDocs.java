package com.wanted.preonboarding.document;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumDocs {

    Map<String, String> redisKey;
    Map<String, String> yn;
    Map<String, String> memberShip;
    Map<String, String> jwtErrorEnum;
    Map<String, String> productStatus;
    Map<String, String> orderType;
    Map<String, String> orderStatus;
    Map<String, String> userRole;

}
