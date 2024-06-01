package com.wanted.preonboarding.domain.user.service;

import com.wanted.preonboarding.domain.user.dto.request.AddUserRequest;

public interface UserService {
    void join(AddUserRequest request);
}
