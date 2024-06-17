package io.taylor.wantedpreonboardingchallengebackend20.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.JoinRequestDto;
import io.taylor.wantedpreonboardingchallengebackend20.member.model.request.LoginRequestDto;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Stream<JoinRequestDto> joinRequestStream() {
        return Stream.of(
                new JoinRequestDto("태경", "taylor", "115taegyeong@gmail.com", "123456789")
        );
    }

    @Order(1)
    @ParameterizedTest
    @DisplayName("[Success] 회원가입 테스트")
    @MethodSource("joinRequestStream")
    public void signupTest(JoinRequestDto request) throws Exception {
        mockMvc.perform(post("/api/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private static Stream<LoginRequestDto> loginRequestStream() {
        return Stream.of(
                new LoginRequestDto("115taegyeong@gmail.com","123456789")
        );
    }

    @Order(2)
    @ParameterizedTest
    @DisplayName("[Success] 로그인 테스트")
    @MethodSource("loginRequestStream")
    void loginTest(LoginRequestDto request) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertNotNull(new JSONObject(mvcResult.getResponse().getContentAsString()).get("accessToken").toString());
    }
}
