package com.example.hs.domain.auth.entity;

import com.example.hs.domain.auth.type.Authority;
import com.example.hs.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder
@Getter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true)
  private String loginId; // 이메일
  @Setter
  private String password;
  private String name;

  @Setter
  @Enumerated(EnumType.STRING)
  private Authority authority;
}
