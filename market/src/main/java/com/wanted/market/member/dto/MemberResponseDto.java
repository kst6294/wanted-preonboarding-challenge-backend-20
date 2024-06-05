package com.wanted.market.member.dto;


import com.wanted.market.member.domain.Member;
import com.wanted.market.member.model.MemberRole;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.dto.ProductResponseDto;
import com.wanted.market.product.model.ProductStatus;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Integer id;

    private String email;

    private String name;

    private String password;

    private String role;

    public static MemberResponseDto createFromEntity(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .role(member.getRole().toString())
                .build();
    }
}
