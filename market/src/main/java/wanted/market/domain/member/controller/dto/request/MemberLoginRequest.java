package wanted.market.domain.member.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.domain.member.service.dto.request.MemberLoginServiceRequest;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {
    private String email;
    private String password;

    @Builder
    private MemberLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public MemberLoginServiceRequest toServiceRequest() {
        return MemberLoginServiceRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}
