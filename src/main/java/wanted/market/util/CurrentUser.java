package wanted.market.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import wanted.market.service.WantedUserDetails;
import wanted.market.entity.User;

@Component
public class CurrentUser {

    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        // 비회원인 경우 anonymous 로 String 객체 반환하는 부분 추가 처리
        if(authentication.getPrincipal() instanceof String){
            return null;
        }

        WantedUserDetails userDetails = (WantedUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
