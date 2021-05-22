package com.gmail.alexandr.tsiulkin.controller.api;

import com.gmail.alexandr.tsiulkin.config.BaseIT;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class OrderAPIControllerIT extends BaseIT {

    @Test
    @Sql({"/scripts/clean_orders.sql", "/scripts/init_orders.sql"})
    void shouldGetAllOrders() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ShowOrderDTO>> response = testRestTemplate.exchange(
                "/api/orders",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(1L, response.getBody().get(0).getNumberOfOrder());
    }
}