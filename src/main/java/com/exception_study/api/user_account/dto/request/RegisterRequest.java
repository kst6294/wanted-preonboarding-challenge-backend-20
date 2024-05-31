package com.exception_study.api.user_account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterRequest {
    private String name;
    private int price;
    private int quantity;

    public static RegisterRequest of(String name, int price, int quantity){
        return new RegisterRequest(name,price,quantity);
    }
}
