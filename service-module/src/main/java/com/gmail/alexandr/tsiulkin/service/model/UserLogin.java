package com.gmail.alexandr.tsiulkin.service.model;

import com.gmail.alexandr.tsiulkin.repository.model.Role;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@Log4j2
@RequiredArgsConstructor
public class UserLogin implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Role role = user.getRole();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
