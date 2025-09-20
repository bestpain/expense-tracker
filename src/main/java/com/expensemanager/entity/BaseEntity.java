package com.expensemanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.Instant;

@Getter
@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    protected void prePersist() {
        if (this.id == null) this.id = java.util.UUID.randomUUID().toString();
        this.createdAt = Instant.now();
    }
}
