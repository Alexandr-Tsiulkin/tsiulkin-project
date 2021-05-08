package com.gmail.alexandr.tsiulkin.config;

import com.gmail.alexandr.tsiulkin.service.model.RoleDTOEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(1)
@RequiredArgsConstructor
public class SecurityAPIConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .antMatchers("/users", "/articles/**")
                                .hasAuthority(RoleDTOEnum.SECURE_API_USER.name())
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }
}
