package wanted.preonboard.market.service.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wanted.preonboard.market.domain.member.dto.MemberDto;
import wanted.preonboard.market.domain.member.Member;
import wanted.preonboard.market.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public boolean insertMember(MemberDto memberDto) {
        return memberMapper.insertMember(memberDto) == 1;
    }

    @Override
    public Member getMemberByEmail(String email) {
        return memberMapper.selectMemberByEmail(email);
    }

}
