package market.market.domain.user.presentation;

import market.market.domain.user.presentation.dto.request.LoginRequest;
import market.market.domain.user.presentation.dto.request.SignUpRequest;
import market.market.domain.user.service.DeleteUserService;
import market.market.domain.user.service.LoginService;
import market.market.domain.user.service.SignUpService;
import market.market.global.security.jwt.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "USER API입니다.")
public class UserController {
    private final SignUpService signUpService;
    private final LoginService loginService;
    private final DeleteUserService deleteUserService;

    @Operation(summary = "회원가입", description = "사용자를 등록합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignUpRequest request) {
        signUpService.execute(request);
    }

    @Operation(summary = "로그인", description = "사용자의 계정에 로그인합니다.")
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request){
        return loginService.execute(request);
    }

    @Operation(summary = "회원 탈퇴", description = "사용자의 계정을 삭제합니다.")
    @DeleteMapping("/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveUser() {
        deleteUserService.execute();
    }
}
