package com.wanted.challenge.transact.entity;

import com.wanted.challenge.base.BaseEntity;
import com.wanted.challenge.transact.model.TransactState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "transact_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transact_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transact_id")
    private Transact transact;

    @Enumerated(EnumType.STRING)
    private TransactState transactState;

    public TransactLog(Transact transact, TransactState transactState) {
        this.transact = transact;
        this.transactState = transactState;
    }
}
