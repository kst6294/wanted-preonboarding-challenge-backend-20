package wanted.preonboard.market.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wanted.preonboard.market.domain.entity.Member;
import wanted.preonboard.market.mapper.MemberMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Autowired
    public CustomUserDetailsService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberMapper.selectMemberByEmail(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUser(member);
    }
}
