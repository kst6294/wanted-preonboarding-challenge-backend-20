package com.example.wantedmarketapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.request.MemberRequestDto.*;
import com.example.wantedmarketapi.dto.response.MemberResponseDto.*;
import com.example.wantedmarketapi.exception.custom.MemberException;
import com.example.wantedmarketapi.repository.MemberRepository;
import com.example.wantedmarketapi.security.provider.JwtAuthProvider;
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

    @Mock
    private JwtAuthProvider jwtAuthProvider;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

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

    @Test
    void login_ShouldReturnTokenResponse_WhenCredentialsAreValid() {
        // Given
        String email = "test@example.com";
        String password = "Test1234!@#$";
        Long memberId = 1L;

        LoginMemberRequest request = new LoginMemberRequest();
        TestUtil.setField(request, "email", email);
        TestUtil.setField(request, "password", password);

        Member member = TestUtil.createMemberWithReflection();
        TestUtil.setField(member, "id", memberId);

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true); // 수정됨
        when(jwtAuthProvider.generateAccessToken(memberId)).thenReturn("access-token");
        when(jwtAuthProvider.generateRefreshToken(memberId)).thenReturn("refresh-token");

        // When
        TokenResponse response = memberCommandService.login(request);

        // Then
        assertEquals(memberId, response.getMemberId());
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
    }


    @Test
    void login_ShouldThrowException_WhenMemberNotFound() {
        // Given
        String email = "test@example.com";
        String password = "Test1234!@#$";

        LoginMemberRequest request = new LoginMemberRequest();
        TestUtil.setField(request, "email", email);
        TestUtil.setField(request, "password", password);

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(MemberException.class, () -> memberCommandService.login(request));
    }

    @Test
    void login_ShouldThrowException_WhenPasswordMismatch() {
        // Given
        String email = "test@example.com";
        String password = "Test1234!@#$";

        LoginMemberRequest request = new LoginMemberRequest();
        TestUtil.setField(request, "email", email);
        TestUtil.setField(request, "password", password);

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(TestUtil.createMemberWithReflection()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false); // Use the Mocked passwordEncoder

        // When & Then
        assertThrows(MemberException.class, () -> memberCommandService.login(request));
    }

}