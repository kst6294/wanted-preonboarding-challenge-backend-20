package org.example.preonboarding.runner;

import lombok.RequiredArgsConstructor;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.model.enums.Role;
import org.example.preonboarding.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializeDataLoader implements CommandLineRunner {
    private final MemberRepository memberRepository;
    BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        if (!memberRepository.existsById(1L)) {
            Member admin = Member.builder()
                    .id(1L)
                    .userId("admin")
                    .password(PASSWORD_ENCODER.encode("admin"))
                    .name("admin")
                    .role(Role.ADMIN)
                    .build();
            memberRepository.save(admin);
        }
    }
}
