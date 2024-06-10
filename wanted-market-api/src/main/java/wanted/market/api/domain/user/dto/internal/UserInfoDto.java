package wanted.market.api.domain.user.dto.internal;

import lombok.Builder;
import lombok.Getter;
import wanted.market.api.domain.product.entity.Product;

@Getter
public class UserInfoDto {
    private Long id;
    private String nickname;


    @Builder
    private UserInfoDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
    public static UserInfoDto from(Product product){
        return UserInfoDto.builder()
                .id(product.getUser().getId())
                .nickname(product.getUser().getNickname())
                .build();
    }
}
