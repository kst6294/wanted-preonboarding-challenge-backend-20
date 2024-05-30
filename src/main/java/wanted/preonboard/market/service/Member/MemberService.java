package wanted.preonboard.market.service.Member;

import wanted.preonboard.market.domain.member.dto.MemberDto;
import wanted.preonboard.market.domain.member.Member;

public interface MemberService {
    boolean insertMember(MemberDto memberDto);
    Member getMemberByEmail(String email);
}
