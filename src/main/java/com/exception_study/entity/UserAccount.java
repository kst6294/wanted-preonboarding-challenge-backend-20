package com.exception_study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class UserAccount {
    @Id
    @Column(name = "user_Id")
    private String userId;
    @Column(name = "password")
    private String password;
    @Column(name = "user_name")
    private String userName;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(userId, that.userId) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password);
    }

    public static UserAccount of(String userId, String password,String userName){
        return new UserAccount(userId,password,userName);
    }
}
