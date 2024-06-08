package com.wanted.challenge.domain.exception.info;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberExceptionInfo {
    EXISTS_MEMBER(HttpStatus.CONFLICT, "MEMBER-001", "이미 가입된 이메일입니다."),
    NOT_EXISTS_MEMBER(HttpStatus.NOT_FOUND, "MEMBER-002", "가입되지 않은 이메일입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER-003", "비밀번호가 일치하지 않습니다."),
    NOT_FOUNT_MEMBER(HttpStatus.NOT_FOUND, "MEMBER-004", "유저 데이터가 존재하지 않습니다."),
    NOT_FOUND_SESSION(HttpStatus.UNAUTHORIZED, "MEMBER-005", "세션이 만료되었거나 유효하지 않습니다.");


    private HttpStatus status;
    private String code;
    private String message;

    MemberExceptionInfo(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
