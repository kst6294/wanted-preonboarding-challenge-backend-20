package com.want.BEProject.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {

    private String userId;

    private String password;

    private String name;

    public static boolean hasNullDataBeforeSignup(UserSignupRequest userSignupRequest) {
        return userSignupRequest.getUserId() == null || userSignupRequest.getPassword() == null || userSignupRequest.getName() == null;
    }
}
