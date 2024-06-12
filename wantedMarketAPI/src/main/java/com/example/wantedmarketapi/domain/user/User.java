package com.example.wantedmarketapi.domain.user;

import com.example.wantedmarketapi.common.exception.InvalidParamException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

/**
 *
 */
@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @Builder
    public User(String username, String email, String password) {
        if (username == null || username.length() == 0) throw new InvalidParamException("empty username");
        if (email == null || email.length() == 0) throw new InvalidParamException("empty email");
        if (password == null || password.length() == 0) throw new InvalidParamException("empty passowrd");

        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }
}
