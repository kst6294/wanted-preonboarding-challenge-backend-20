package org.example.wantedpreonboardingchallengebackend20.common.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommonErrorResponse<T> {
    private String error_code;
    private String error_desc;
    private String error_message;
}
