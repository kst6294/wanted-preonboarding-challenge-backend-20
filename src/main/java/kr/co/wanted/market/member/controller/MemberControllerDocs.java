package kr.co.wanted.market.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.wanted.market.common.global.dto.ApiResult;
import kr.co.wanted.market.member.dto.MemberJoin;
import kr.co.wanted.market.member.dto.MemberLogin;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 API", description = "회원 관련 API")
public interface MemberControllerDocs {

    @Operation(
            summary = "회원가입",
            description = "회원 가입합니다.",
            requestBody = @RequestBody(content = @Content(
                    examples = {
                            @ExampleObject(name = "예시", value = """
                                    {
                                        "id": "abcdef",
                                        "password": "12345"
                                    }
                                    """)
                    }
            ))
    )
    ResponseEntity<Void> join(MemberJoin memberJoin);

    @Operation(
            summary = "로그인",
            description = "로그인 합니다.",
            requestBody = @RequestBody(content = @Content(
                    examples = {
                            @ExampleObject(name = "예시", value = """
                                    {
                                        "id": "abcdef",
                                        "password": "12345"
                                    }
                                    """)
                    }
            ))
    )
    ResponseEntity<ApiResult<MemberLogin.Response>> login(MemberLogin.Request loginRequest);

}
