package com.gmail.alexandr.tsiulkin.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.gmail.alexandr.tsiulkin.repository.model")
public class PersistenceConfig {
}
