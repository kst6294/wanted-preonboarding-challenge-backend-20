package com.wanted.preonboarding.document.utils;

import com.wanted.preonboarding.auth.core.AuthToken;
import com.wanted.preonboarding.auth.core.UserPrincipal;
import com.wanted.preonboarding.data.auth.AuthModuleHelper;
import com.wanted.preonboarding.module.user.core.BaseUserInfo;
import com.wanted.preonboarding.module.user.entity.Users;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class SecuritySupportTest {

    protected String token;

    protected void securityUserMockSetting() {
        AuthToken authToken = AuthModuleHelper.toJwtAuthToken_another_constructor();
        token = authToken.getToken();
    }


    protected void securityUserMockSetting(Users users) {
        BaseUserInfo userInfo = AuthModuleHelper.toBaseUserInfo(users);
        UserPrincipal userPrincipal = UserPrincipal.toUserPrincipal(userInfo);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal.getPassword(), userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        AuthToken authToken = AuthModuleHelper.toJwtAuthToken_another_constructor(userInfo.getEmail());
        token = authToken.getToken();
    }


}
