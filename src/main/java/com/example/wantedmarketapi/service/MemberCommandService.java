package com.example.wantedmarketapi.service;

import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.MemberRequestDto.*;

public interface MemberCommandService {

    Member signUpMember(SignUpMemberRequest request);

}
