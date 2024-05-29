package wanted.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wanted.market.entity.User;
import wanted.market.repository.UserRepository;

@Service
public class WantedUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // ID값으로 유저 정보 로드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new WantedUserDetails(user);
    }
}
