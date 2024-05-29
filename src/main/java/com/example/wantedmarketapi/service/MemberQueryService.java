package com.example.wantedmarketapi.service;

import com.example.wantedmarketapi.domain.member.Member;

import java.util.Optional;

public interface MemberQueryService {

    Member findMemberById(Long id);

}
