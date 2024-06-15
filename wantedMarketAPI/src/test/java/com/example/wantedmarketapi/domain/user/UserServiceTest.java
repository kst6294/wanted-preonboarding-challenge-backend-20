package com.example.wantedmarketapi.domain.user;

import com.example.wantedmarketapi.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class UserServiceTest extends IntegrationTestSupport {


    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("유저 등록")
    void registerUser() {
        UserCommand command = UserCommand.builder()
                .email("email")
                .username("username")
                .password("password")
                .build();


        UserInfo userInfo = userService.registerUser(command);

        Assertions.assertThat(command.getEmail()).isEqualTo(userInfo.getEmail());
        Assertions.assertThat(command.getUsername()).isEqualTo(userInfo.getUsername());
    }


}
