package wanted.preonboard.market.service.Member;

import wanted.preonboard.market.domain.dto.MemberDto;
import wanted.preonboard.market.domain.entity.Member;

public interface MemberService {
    boolean insertMember(MemberDto memberDto);
    Member getMemberByEmail(String email);
}
