package com.example.wanted.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long u_id;
    private String email;
    private String password;
    private String name;
    private String role;

    @Builder
    public User(Long u_id, String email, String password, String name, String role) {
        this.u_id = u_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
