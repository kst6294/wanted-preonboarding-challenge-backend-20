package com.wanted.market.global.exception;

import lombok.Getter;

@Getter
public class UnauthorizedUserException extends RuntimeException{
    public UnauthorizedUserException(){
        super("존재하지 않는 회원 정보입니다. 로그인 후 이용해 주세요.");
    }
}
