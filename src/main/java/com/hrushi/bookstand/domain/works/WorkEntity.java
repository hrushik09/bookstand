package com.hrushi.bookstand.domain.works;

import com.hrushi.bookstand.domain.authors.AuthorEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "works", uniqueConstraints = @UniqueConstraint(name = "UK_works_open_library_key", columnNames = "open_library_key"))
class WorkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String openLibraryKey;
    @Column(nullable = false)
    private String title;
    private String subtitle;
    private String coverId;
    @ManyToMany
    @JoinTable(name = "works_authors", joinColumns = @JoinColumn(name = "work_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<AuthorEntity> authors = new HashSet<>();
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

    public String getOpenLibraryKey() {
        return openLibraryKey;
    }

    public void setOpenLibraryKey(String openLibraryKey) {
        this.openLibraryKey = openLibraryKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
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
