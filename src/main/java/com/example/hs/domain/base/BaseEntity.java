package com.example.hs.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
  @CreatedDate
  @Column(updatable = false) // 업데이트 후 null 되는 것 방지
  private LocalDateTime createDateTime;

  @LastModifiedDate
  private LocalDateTime updateDateTime;
}

