package wanted.preonboard.market.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wanted.preonboard.market.domain.entity.Member;
import wanted.preonboard.market.message.MemberExceptionMessage;
import wanted.preonboard.market.service.Member.MemberService;

import java.util.Collections;

@Component
public class CustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUsernamePasswordAuthenticationProvider(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Member member = memberService.getMemberByEmail(username);
        if (member != null) {
            if (passwordEncoder.matches(password, member.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(
                        new SimpleGrantedAuthority("USER")
                ));
            } else {
                throw new BadCredentialsException(MemberExceptionMessage.WRONG_PASSWORD.getMessage());
            }
        } else {
            throw new BadCredentialsException(MemberExceptionMessage.USER_NOT_FOUND.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
