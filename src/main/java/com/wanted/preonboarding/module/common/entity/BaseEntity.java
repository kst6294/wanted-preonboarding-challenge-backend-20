package com.wanted.preonboarding.module.common.entity;

import com.wanted.preonboarding.module.common.enums.Yn;
import jakarta.persistence.*;
import lombok.Getter;
import org.slf4j.MDC;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    private static final String USER = "user";
    private static final String GUEST = "guest";

    @Enumerated(EnumType.STRING)
    @Column(name = "DELETE_YN", nullable = false)
    private Yn deleteYn;

    @Column(updatable = false, name = "INSERT_DATE", nullable = false)
    private LocalDateTime insertDate;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "INSERT_OPERATOR", nullable = false)
    private String insertOperator;

    @Column(name = "UPDATE_OPERATOR", nullable = false)
    private String updateOperator;

    @PrePersist
    public void before() {
        LocalDateTime now = LocalDateTime.now();
        this.insertDate = now;
        this.updateDate = now;
        this.insertOperator = getOperator();
        this.updateOperator = getOperator();
        this.deleteYn = Yn.N;
    }

    @PreUpdate
    public void always() {
        this.updateDate = LocalDateTime.now();
        this.updateOperator = getOperator();
    }

    private String getOperator() {
        return MDC.get(USER) != null ? MDC.get(USER) : GUEST;
    }


}
