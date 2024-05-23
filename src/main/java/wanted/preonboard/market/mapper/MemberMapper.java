package wanted.preonboard.market.mapper;

import org.apache.ibatis.annotations.Mapper;
import wanted.preonboard.market.domain.dto.MemberDto;
import wanted.preonboard.market.domain.entity.Member;

@Mapper
public interface MemberMapper {

    int insertMember(MemberDto memberDto);
    Member selectMemberByEmail(String email);
}
