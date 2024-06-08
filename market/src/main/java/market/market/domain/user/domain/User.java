package market.market.domain.user.domain;

import market.market.domain.product.domain.Product;
import market.market.domain.user.enums.Role;
import market.market.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Getter
@Table(name = "tbl_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private String email; // 이메일

    private String accountId; // 아이디

    @Length(max = 68)
    private String password; // 비밀번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 회원인지 아닌지

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Product> products; // 상품들

    @Builder
    public User(String email, String accountId, String password) {
        this.email = email;
        this.accountId = accountId;
        this.password = password;
        this.role = Role.Role_Member;
    }
}
