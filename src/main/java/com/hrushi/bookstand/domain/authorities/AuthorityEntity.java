package com.hrushi.bookstand.domain.authorities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "authorities", uniqueConstraints = @UniqueConstraint(name = "UK_authorities_value", columnNames = "value"))
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String value;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    protected AuthorityEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
