package io.github.potatoy.wanted_preonboarding_challenge.token;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.potatoy.wanted_preonboarding_challenge.config.jwt.JwtProperties;
import io.github.potatoy.wanted_preonboarding_challenge.config.jwt.TokenProvider;
import io.github.potatoy.wanted_preonboarding_challenge.user.dto.AddUserRequest;
import io.github.potatoy.wanted_preonboarding_challenge.user.entity.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class TokenApiControllerTest {

  @Autowired protected MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper; // JSON 직렬화, 역직렬화를 위한 클래스
  @Autowired private WebApplicationContext context;
  @Autowired JwtProperties jwtProperties;
  @Autowired UserRepository userRepository;
  @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired TokenProvider tokenProvider;

  @BeforeEach
  public void mockMvcSetup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    userRepository.deleteAll();
  }

  @DisplayName("authenticate(): 로그인 성공")
  @Test
  public void successAuthentication() throws Exception {
    // given 새로운 유저 추가에 필요한 객체를 생성
    final String url = "/api/auth/signup";
    final String email = "test@mail.com";
    final String password = "test";
    final String nickname = "test user";
    final AddUserRequest addUserRequest = new AddUserRequest(email, password, nickname);

    // 객체 JSON으로 직렬화
    final String requestBody = objectMapper.writeValueAsString(addUserRequest);

    // when 회원가입 요청을 보낸다.
    ResultActions result =
        mockMvc.perform(
            post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));

    // then 응답코드가 201인지 확인
    result
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.email").value(email))
        .andExpect(jsonPath("$.nickname").value(nickname));
  }
}
