package org.example.preonboarding.member.model.mapper;



import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.model.payload.request.SignupRequest;
import org.example.preonboarding.member.model.payload.response.MemberResponse;
import org.example.preonboarding.member.model.payload.response.SignupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper
public interface MemberMapper {
    MemberMapper INSTNACE = Mappers.getMapper(MemberMapper.class);
    BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Mapping(target = "password", source = "password", qualifiedByName = "passwordEncoding")
    Member toMember(SignupRequest memberRequest);
    SignupResponse toSignupResponse(Member member);
    MemberResponse toMemberResponse(Member member);

    @Named("passwordEncoding")
    default String passwordEncoding(String password) {
        return PASSWORD_ENCODER.encode(password);
    }
}
