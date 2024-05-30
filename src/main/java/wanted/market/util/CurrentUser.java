package wanted.market.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import wanted.market.service.WantedUserDetails;
import wanted.market.entity.User;

@Component
public class CurrentUser {

    public User getUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new Exception("사용자 인증이 필요합니다.");
        }

        WantedUserDetails userDetails = (WantedUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
