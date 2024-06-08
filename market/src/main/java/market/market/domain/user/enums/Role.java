package market.market.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    Role_Member("MEMBER", "회원");

    private final String key;
    private final String title;
}
