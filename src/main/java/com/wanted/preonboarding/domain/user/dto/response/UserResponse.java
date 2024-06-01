package com.wanted.preonboarding.domain.user.dto.response;

import com.wanted.preonboarding.domain.user.entity.User;
import com.wanted.preonboarding.domain.user.entity.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private UserType type;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .type(user.getType())
                .build();
    }
}
