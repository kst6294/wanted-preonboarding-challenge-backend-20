package wanted.market.domain.member.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.domain.member.service.dto.request.MemberJoinServiceRequest;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {
    private String email;
    private String password;

    @Builder
    private MemberJoinRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public MemberJoinServiceRequest toServiceRequest() {
        return MemberJoinServiceRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}
