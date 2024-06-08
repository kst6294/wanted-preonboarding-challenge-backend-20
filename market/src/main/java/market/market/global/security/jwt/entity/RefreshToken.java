package market.market.global.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String accountId;

    private String refreshToken;

    private Long rtkTime;

}
