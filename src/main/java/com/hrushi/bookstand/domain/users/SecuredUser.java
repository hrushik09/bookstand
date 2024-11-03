package com.hrushi.bookstand.domain.users;

import com.hrushi.bookstand.domain.authorities.AuthorityEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

record SecuredUser(String username, String password, List<SimpleGrantedAuthority> authorities) implements UserDetails {
    public SecuredUser(UserEntity userEntity) {
        this(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getAuthorities().stream()
                        .map(AuthorityEntity::getValue)
                        .map(SimpleGrantedAuthority::new)
                        .toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
