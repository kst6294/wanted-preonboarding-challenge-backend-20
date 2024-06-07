package wanted.market.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import wanted.market.domain.member.controller.dto.request.MemberJoinRequest;
import wanted.market.domain.member.controller.dto.request.MemberLoginRequest;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.service.MemberService;
import wanted.market.domain.member.service.dto.request.MemberJoinServiceRequest;

import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @TestFactory
    @DisplayName("이메일 중복 조회 시나리오")
    Collection<DynamicTest> emailCheck() {
        // given
        MemberJoinServiceRequest request = createRequestMember("test@test.com");

        memberService.join(request);

        String possibleEmail = "test2@test.com";
        String impossibleEmail = "test@test.com";

        return List.of(
                DynamicTest.dynamicTest("중복되지 않은 이메일이므로 사용 가능하다.", () -> {
                    // when & then
                    mockMvc.perform(
                                    get("/api/v1/member/check-email").queryParam("email", possibleEmail)
                            ).andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.data").value("true"));
                }),
                DynamicTest.dynamicTest("중복된 이메일이므로 사용 불가능하다.", () -> {
                    // when & then
                    mockMvc.perform(
                                    get("/api/v1/member/check-email").queryParam("email", impossibleEmail)
                            ).andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.data").value("false"));
                })
        );
    }

    @Test
    @DisplayName("회원가입을 시도한다.")
    void join() throws Exception {
        // given
        MemberJoinRequest request = MemberJoinRequest.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        // when & then
        mockMvc.perform(
                        post("/api/v1/member/join").content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("true"));
    }

    @Test
    @DisplayName("로그인을 시도한다.")
    void login() throws Exception {
        // given
        MemberJoinRequest joinRequest = MemberJoinRequest.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        memberService.join(joinRequest.toServiceRequest());

        MemberLoginRequest request = MemberLoginRequest.builder()
                .email(joinRequest.getEmail())
                .password(joinRequest.getPassword())
                .build();


        // when & then
        mockMvc.perform(
                        post("/api/v1/member/login").content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    private static MemberJoinServiceRequest createRequestMember(String email) {
        return MemberJoinServiceRequest.builder()
                .email(email)
                .password("1234")
                .build();
    }
}