package com.wanted.preonboarding.backend20.domain.member.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.member.dto.SignUpDto;
import com.wanted.preonboarding.backend20.domain.member.repository.MemberRepository;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("회원가입 이메일 중복 테스트 - 예외발생")
    void signUp_duplicated_email() {
        SignUpDto dto = new SignUpDto("asdf@naver.com","password","name");
        when(memberRepository.existsByEmail("asdf@naver.com")).thenReturn(true);
        Assertions.assertThatThrownBy(() -> authService.signUp(dto))
                        .isExactlyInstanceOf(CustomException.class)
                                .hasMessageContaining("이미");
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp() {
        SignUpDto dto = new SignUpDto("asdf@naver.com","password","name");
        when(memberRepository.existsByEmail("asdf@naver.com")).thenReturn(false);

        authService.signUp(dto);
        verify(memberRepository, times(1)).save(any(Member.class));
    }
}