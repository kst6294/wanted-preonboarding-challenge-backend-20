package io.github.potatoy.wanted_preonboarding_challenge.user.entity;

import io.github.potatoy.wanted_preonboarding_challenge.market.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "nickname", nullable = false)
  private String nickname;

  @OneToMany(mappedBy = "sellerUser", cascade = CascadeType.ALL)
  private List<Product> saleList = new ArrayList<>();

  @OneToMany(mappedBy = "buyerUser", cascade = CascadeType.ALL)
  private List<Product> purchaseList = new ArrayList<>();

  @Builder
  public User(String email, String password, String nickname) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("user"));
  }

  @Override
  public String getUsername() {
    return email;
  }
}
