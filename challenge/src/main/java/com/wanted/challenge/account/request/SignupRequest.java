package com.wanted.challenge.account.request;

import jakarta.validation.constraints.NotEmpty;

public record SignupRequest(@NotEmpty String loginId, @NotEmpty String password) {

}
