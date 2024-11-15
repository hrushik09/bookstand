package com.hrushi.bookstand.domain.works;

import com.hrushi.bookstand.domain.users.UserEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "work_review_likes")
class WorkReviewLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private UserEntity userEntity;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "work_review_id", referencedColumnName = "id", nullable = false, updatable = false)
    private WorkReviewEntity workReviewEntity;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

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

    public WorkReviewEntity getWorkReviewEntity() {
        return workReviewEntity;
    }

    public void setWorkReviewEntity(WorkReviewEntity workReviewEntity) {
        this.workReviewEntity = workReviewEntity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
