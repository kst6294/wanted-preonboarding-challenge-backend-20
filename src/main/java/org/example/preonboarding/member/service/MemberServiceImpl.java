package org.example.preonboarding.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.preonboarding.member.exception.WithdrawException;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.model.mapper.MemberMapper;
import org.example.preonboarding.member.model.payload.request.SignupRequest;
import org.example.preonboarding.member.model.payload.response.MemberResponse;
import org.example.preonboarding.member.model.payload.response.SignupResponse;
import org.example.preonboarding.member.model.payload.response.WithdrawResponse;
import org.example.preonboarding.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public SignupResponse createUser(SignupRequest signupRequest) {
        Member savedMember = memberRepository.save(MemberMapper.INSTNACE.toMember(signupRequest));
        return MemberMapper.INSTNACE.toSignupResponse(savedMember);
    }

    @Override
    public List<MemberResponse> getUsers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberMapper.INSTNACE::toMemberResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MemberResponse getUserByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(() ->
                new RuntimeException(userId)); // TODO: spring security 추가 후 UsernameNotFoundException으로 변경
        return MemberMapper.INSTNACE.toMemberResponse(member);
    }

    @Override
    @Transactional
    public WithdrawResponse deleteUser(String userId) throws WithdrawException {
        int deleteCount = memberRepository.deleteByUserId(userId);
        if(deleteCount < 1) {
            throw new WithdrawException();
        }
        return WithdrawResponse.builder()
                .deletedCount(deleteCount)
                .build();
    }
}
