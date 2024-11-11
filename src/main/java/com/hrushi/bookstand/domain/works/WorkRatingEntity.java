package com.hrushi.bookstand.domain.works;

import com.hrushi.bookstand.domain.users.UserEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "work_ratings")
class WorkRatingEntity {
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
    private Integer rating;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    protected WorkRatingEntity() {
    }

    WorkRatingEntity(UserEntity userEntity, WorkEntity workEntity) {
        this.userEntity = userEntity;
        this.workEntity = workEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userId) {
        this.userEntity = userId;
    }

    public WorkEntity getWorkEntity() {
        return workEntity;
    }

    public void setWorkEntity(WorkEntity workId) {
        this.workEntity = workId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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
