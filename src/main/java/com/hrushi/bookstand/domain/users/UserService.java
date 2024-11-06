package com.hrushi.bookstand.domain.users;

import com.hrushi.bookstand.domain.authorities.AuthorityEntity;
import com.hrushi.bookstand.domain.authorities.AuthorityService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityService authorityService;

    UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthorityService authorityService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authorityService = authorityService;
    }

    @Transactional
    public void createUser(CreateUserCommand cmd) {
        if (userRepository.existsByUsername(cmd.username())) {
            throw new UserAlreadyExists(cmd.username());
        }
        Set<AuthorityEntity> authorities = cmd.authorities().stream()
                .map(authorityService::findAuthorityEntityByValue)
                .collect(Collectors.toSet());
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(cmd.username());
        userEntity.setPassword(passwordEncoder.encode(cmd.password()));
        userEntity.setAuthorities(authorities);
        userRepository.save(userEntity);
    }

    public List<String> findAll() {
        return userRepository.findAll().stream()
                .map(UserEntity::getUsername)
                .toList();
    }

    public UserEntity getReferenceById(Long id) {
        return userRepository.getReferenceById(id);
    }
}
