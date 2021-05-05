package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.model.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}", username);
        User user = userRepository.findUserByUsername(username);
        log.info("user with username: {} found with role: {}", user.getEmail(), user.getRole());
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User with username: " + username + " was not found");
        }
        return new UserLogin(user);
    }
}
