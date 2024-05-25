package wanted.preonboard.market.config;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import wanted.preonboard.market.domain.entity.Member;

import java.util.List;

@Getter
public class CustomUser extends User {
    private final Member member;

    public CustomUser(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }
}
