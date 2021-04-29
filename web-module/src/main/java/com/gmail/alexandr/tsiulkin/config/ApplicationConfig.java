package com.gmail.alexandr.tsiulkin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@ComponentScan(basePackages = {"com.gmail.alexandr.tsiulkin.repository", "com.gmail.alexandr.tsiulkin.service"})
public class ApplicationConfig {

    @Bean
    public Random random() {
        return new Random();
    }
}
