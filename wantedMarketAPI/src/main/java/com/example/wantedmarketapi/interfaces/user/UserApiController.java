package com.example.wantedmarketapi.interfaces.user;

import com.example.wantedmarketapi.application.user.UserFacade;
import com.example.wantedmarketapi.domain.user.UserCommand;
import com.example.wantedmarketapi.domain.user.UserInfo;
import com.example.wantedmarketapi.interfaces.user.UserDto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid UserDto.RegisterRequest request) {
        UserCommand command = request.toCommand();
        UserInfo userInfo = userFacade.registerUser(command);
        RegisterResponse response = new RegisterResponse(userInfo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);

    }
}
