package wanted.preonboard.market.message;

import lombok.Getter;

@Getter
public enum MemberMessage {
    ERROR("회원가입 중 알 수 없는 오류가 발생했습니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS("이미 존재하는 사용자입니다."),
    USER_NOT_AUTHORIZED("사용자 권한이 없습니다."),
    USER_NOT_LOGGED_IN("로그인이 필요합니다."),
    REGISTERED_SUCCESSFULLY("회원가입이 완료되었습니다."),;

    private final String message;

    MemberMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
