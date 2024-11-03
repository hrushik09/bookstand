package com.hrushi.bookstand.domain.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class SecuredUserService implements UserDetailsService {
    private final UserRepository userRepository;

    SecuredUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(SecuredUser::new)
                .orElseThrow(() -> new UserDoesNotExist(username));
    }
}