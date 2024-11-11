package com.hrushi.bookstand.domain.works;

import com.hrushi.bookstand.domain.users.UserEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "work_reviews")
class WorkReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private UserEntity userEntity;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id", referencedColumnName = "id", nullable = false, updatable = false)
    private WorkEntity workEntity;
    @Column(nullable = false)
    private String review;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public WorkEntity getWorkEntity() {
        return workEntity;
    }

    public void setWorkEntity(WorkEntity workEntity) {
        this.workEntity = workEntity;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
