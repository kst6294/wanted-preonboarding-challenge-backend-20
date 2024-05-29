package org.example.preonboarding.member.service;


import org.example.preonboarding.member.exception.WithdrawException;
import org.example.preonboarding.member.model.payload.request.SignupRequest;
import org.example.preonboarding.member.model.payload.response.MemberResponse;
import org.example.preonboarding.member.model.payload.response.SignupResponse;
import org.example.preonboarding.member.model.payload.response.WithdrawResponse;

import java.util.List;

public interface MemberService {
    SignupResponse createUser(SignupRequest signupRequest);

    MemberResponse getUserByUserId(String userId);

    List<MemberResponse> getUsers();

    WithdrawResponse deleteUser(String userId) throws WithdrawException;
}
