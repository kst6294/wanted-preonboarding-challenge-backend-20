package io.taylor.wantedpreonboardingchallengebackend20.common.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommonResponse<T> {
    private boolean result;
    private String message;
    private String desc;
    private T data;
}
