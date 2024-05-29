package org.example.preonboarding.member.model.payload.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignupResponse {
    private String userId;
    private String name;
}
