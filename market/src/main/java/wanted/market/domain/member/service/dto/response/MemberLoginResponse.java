package wanted.market.domain.member.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginResponse {
    private String accessToken;
    private String name;
    private String email;

    @Builder
    private MemberLoginResponse(String accessToken, String name, String email) {
        this.accessToken = accessToken;
        this.name = name;
        this.email = email;
    }
}
