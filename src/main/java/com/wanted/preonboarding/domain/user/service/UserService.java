package com.wanted.preonboarding.domain.user.service;

import com.wanted.preonboarding.domain.user.dto.request.AddUserRequest;
import com.wanted.preonboarding.domain.user.dto.request.CheckEmailRequest;
import com.wanted.preonboarding.domain.user.dto.request.LoginUserRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    void join(AddUserRequest addUserRequest);
    void login(HttpServletRequest request, LoginUserRequest loginUserRequest);
    boolean checkEmail(CheckEmailRequest checkEmailRequest);
}
