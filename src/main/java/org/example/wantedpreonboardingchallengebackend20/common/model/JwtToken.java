package org.example.wantedpreonboardingchallengebackend20.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtToken {
    private final String grantType;
    private final String authorizationType;
    private final String accessToken;
    private final Long accessTokenExpiresIn;
}
