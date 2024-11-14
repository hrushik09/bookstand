package com.hrushi.bookstand.domain.profiles;

import com.hrushi.bookstand.domain.Country;
import com.hrushi.bookstand.domain.Icon;
import com.hrushi.bookstand.domain.users.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "profiles", uniqueConstraints = {@UniqueConstraint(name = "UK_profiles_user_id", columnNames = "user_id"), @UniqueConstraint(name = "UK_profiles_email", columnNames = "email")})
class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false, updatable = false)
    private UserEntity userEntity;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;
    @Column(unique = true)
    private String email;
    private String bio;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Icon icon;

    protected ProfileEntity() {
    }

    public ProfileEntity(UserEntity userEntity, Icon icon) {
        this.userEntity = userEntity;
        this.icon = icon;
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

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
