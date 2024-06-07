package wanted.market.domain.member.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginResponseDto {
    private String accessToken;
    private String name;
    private String email;

    @Builder
    private MemberLoginResponseDto(String accessToken, String name, String email) {
        this.accessToken = accessToken;
        this.name = name;
        this.email = email;
    }
}
