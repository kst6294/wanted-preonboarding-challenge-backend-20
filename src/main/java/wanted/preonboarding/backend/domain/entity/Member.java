package wanted.preonboarding.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.preonboarding.backend.domain.BaseTimeEntity;
import wanted.preonboarding.backend.dto.request.MemberSaveRequest;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;

    public static Member from(final MemberSaveRequest memberSaveRequest) {
        return Member.builder()
                .email(memberSaveRequest.getEmail())
                .password(memberSaveRequest.getPassword())
                .name(memberSaveRequest.getName())
                .nickname(memberSaveRequest.getNickname())
                .build();
    }
}
