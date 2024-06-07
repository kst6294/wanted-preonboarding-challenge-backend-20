package wanted.market.domain.member.service;

import wanted.market.domain.member.service.dto.request.MemberJoinServiceRequest;
import wanted.market.domain.member.service.dto.request.MemberLoginServiceRequest;
import wanted.market.domain.member.service.dto.response.MemberLoginResponse;

public interface MemberService {

    boolean checkEmail(String email);

    boolean join(MemberJoinServiceRequest request);

    MemberLoginResponse login(MemberLoginServiceRequest request);
}
