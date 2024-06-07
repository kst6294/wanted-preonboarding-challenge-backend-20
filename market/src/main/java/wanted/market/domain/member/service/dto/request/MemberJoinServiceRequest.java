package wanted.market.domain.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.global.dto.Authority;

import java.time.LocalDateTime;

import static wanted.market.global.dto.Authority.*;

@Getter
public class MemberJoinServiceRequest {

    private String email;
    private String password;

    @Builder
    private MemberJoinServiceRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(ROLE_USER)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
