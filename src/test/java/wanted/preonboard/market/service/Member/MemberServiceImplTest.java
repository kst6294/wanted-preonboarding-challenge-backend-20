package wanted.preonboard.market.service.Member;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import wanted.preonboard.market.domain.dto.MemberDto;
import wanted.preonboard.market.domain.entity.Member;
import wanted.preonboard.market.mapper.MemberMapper;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceImplTest {
    private final MemberMapper memberMapper = Mockito.mock(MemberMapper.class);
    private final MemberService memberService = new MemberServiceImpl(memberMapper);

    @Test
    void getMemberByEmail() {
        String memberEmail = "john@example.com";
        Member mockMember = new Member(
            1L, "John Doe", memberEmail, "password"
        );

        Mockito.when(memberMapper.selectMemberByEmail(memberEmail))
            .thenReturn(mockMember);

        Member result = memberService.getMemberByEmail(memberEmail);

        assertEquals(mockMember, result);
    }

    @Test
    void insertMember() {
        String memberEmail = "john@example.com";
        MemberDto mockMember = new MemberDto(
            "John Doe", memberEmail, "password"
        );

        Mockito.when(memberMapper.insertMember(mockMember))
            .thenReturn(1);

        boolean result = memberService.insertMember(mockMember);

        assertTrue(result);
    }
}