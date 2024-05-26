package wanted.preonboarding.backend.dto.request;

import lombok.Getter;

@Getter
public class MemberSaveRequest {

    private String email;
    private String password;
    private String name;
    private String nickname;
}
