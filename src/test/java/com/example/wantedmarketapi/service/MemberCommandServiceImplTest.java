package com.example.wantedmarketapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.util.Optional;

import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.MemberRequestDto.*;
import com.example.wantedmarketapi.exception.custom.MemberException;
import com.example.wantedmarketapi.repository.MemberRepository;
import com.example.wantedmarketapi.service.impl.MemberCommandServiceImpl;
import com.example.wantedmarketapi.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@ExtendWith(MockitoExtension.class)
public class MemberCommandServiceImplTest {

    @InjectMocks
    private MemberCommandServiceImpl memberCommandService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void signUpMember_NewMember_ShouldSucceed() {
        // Given
        String email = "test@example.com";
        String password = "Test1234!@#$";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        SignUpMemberRequest request = new SignUpMemberRequest();
        TestUtil.setField(request, "email", email);
        TestUtil.setField(request, "password", password);

        // When
        Member result = memberCommandService.signUpMember(request);

        // Then
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertTrue(encoder.matches(password, result.getPassword().getEncryptedPassword()), "The password should be encrypted and match");

        verify(memberRepository, times(1)).findByEmail(email);
        verify(memberRepository, times(1)).save(any(Member.class));
    }


    public static Member createMemberWithReflection() {
        try {
            Constructor<Member> constructor = Member.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void signUpMember_ExistingMember_ShouldThrowException() {
        // Given
        String email = "exist@example.com";
        String password = "Test1234!@#$";
        SignUpMemberRequest request = new SignUpMemberRequest();
        TestUtil.setField(request, "email", email);
        TestUtil.setField(request, "password", password);

        // 여기서 리플렉션을 사용하여 Member 객체를 생성합니다.
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(TestUtil.createMemberWithReflection()));

        // When & Then
        assertThrows(MemberException.class, () -> memberCommandService.signUpMember(request));

        verify(memberRepository, times(1)).findByEmail(email);
        verify(memberRepository, never()).save(any(Member.class));
    }
}