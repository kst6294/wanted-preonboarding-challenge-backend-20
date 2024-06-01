package io.github.potatoy.wanted_preonboarding_challenge.token;

import io.github.potatoy.wanted_preonboarding_challenge.token.dto.AuthenticateDto;
import io.github.potatoy.wanted_preonboarding_challenge.token.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class TokenApiController {

  private final TokenService tokenService;

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticateDto.Response> authenticate(
      @RequestBody AuthenticateDto.Request request) {
    return ResponseEntity.status(HttpStatus.OK).body(tokenService.createNewTokenSet(request));
  }

  @PostMapping("/token")
  public ResponseEntity<TokenDto.Response> token(@RequestBody TokenDto.Request request) {
    String accessToken = tokenService.createNewAccessToken(request);

    return ResponseEntity.status(HttpStatus.OK).body(new TokenDto.Response(accessToken));
  }
}
