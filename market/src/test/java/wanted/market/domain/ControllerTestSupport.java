package wanted.market.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.domain.member.controller.MemberController;
import wanted.market.domain.member.repository.MemberRepository;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.member.service.MemberService;
import wanted.market.domain.member.service.dto.request.MemberJoinServiceRequest;
import wanted.market.domain.member.service.dto.request.MemberLoginServiceRequest;
import wanted.market.domain.member.service.dto.response.MemberLoginResponse;
import wanted.market.domain.product.controller.ProductController;
import wanted.market.domain.product.repository.ProductRepository;
import wanted.market.domain.product.service.ProductService;
import wanted.market.domain.transcation.repository.TransactionRepository;
import wanted.market.domain.transcation.service.TransactionService;

@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected TransactionService transactionService;

    @Autowired
    protected TransactionRepository transactionRepository;

    @Autowired
    protected MemberService memberService;


    protected String email;
    protected String email2;

    protected String accessToken;
    protected String accessToken2;

    @BeforeEach
    void setUp() {
        // 테스트용 멤버 객체 생성 및 저장
        memberService.join(MemberJoinServiceRequest.builder()
                .email("testuser@example.com")
                .password("password")
                .build());

        memberService.join(MemberJoinServiceRequest.builder()
                .email("testuser2@example.com")
                .password("password")
                .build());

        accessToken = memberService.login(MemberLoginServiceRequest.builder()
                .email("testuser@example.com")
                .password("password")
                .build()).getAccessToken();

        accessToken2 = memberService.login(MemberLoginServiceRequest.builder()
                .email("testuser2@example.com")
                .password("password")
                .build()).getAccessToken();

        email = "testuser@example.com";
        email2 = "testuser2@example.com";;
    }

    @AfterEach
    void tearDown() {
//        productRepository.deleteAllInBatch();
    }
}
