package com.gmail.alexandr.tsiulkin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.gmail.alexandr.tsiulkin.repository","com.gmail.alexandr.tsiulkin.service"})
public class ApplicationConfig {
}
