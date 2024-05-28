package com.example.wantedmarketapi.service;

import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.MemberRequestDto.*;
import com.example.wantedmarketapi.dto.response.MemberResponseDto.*;

public interface MemberCommandService {

    Member signUpMember(SignUpMemberRequest request);

    TokenResponse login(LoginMemberRequest request);

}
