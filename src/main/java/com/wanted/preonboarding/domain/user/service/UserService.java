package com.wanted.preonboarding.domain.user.service;

import com.wanted.preonboarding.domain.user.dto.request.AddUserRequest;
import com.wanted.preonboarding.domain.user.dto.request.LoginUserRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    void join(AddUserRequest request);
    void login(HttpServletRequest request, LoginUserRequest loginUserRequest);
}
