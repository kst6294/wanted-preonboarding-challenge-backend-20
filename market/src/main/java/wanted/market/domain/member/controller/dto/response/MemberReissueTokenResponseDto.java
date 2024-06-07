package wanted.market.domain.member.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberReissueTokenResponseDto {
    private String accessToken;

    @Builder
    private MemberReissueTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
