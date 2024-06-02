package com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response;

import lombok.*;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponseDto<T> {
    private HttpStatus statusCode;
    private String resultMessage;
    private T data;
}
