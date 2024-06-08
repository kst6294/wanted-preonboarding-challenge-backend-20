package market.market.global.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private final String atk;
    private final String rtk;
    private final Long expiredAt;
}
