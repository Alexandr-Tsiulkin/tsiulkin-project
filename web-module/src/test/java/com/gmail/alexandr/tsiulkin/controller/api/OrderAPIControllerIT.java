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

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ORDERS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class OrderAPIControllerIT extends BaseIT {

    @Test
    @Sql({"/scripts/clean_orders.sql", "/scripts/init_orders.sql"})
    void shouldGetAllOrders() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ShowOrderDTO>> response = testRestTemplate.exchange(
                REST_API_USER_PATH + ORDERS_PATH,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c"), response.getBody().get(0).getNumberOfOrder());
        assertEquals(BigDecimal.valueOf(500), response.getBody().get(0).getTotalPrice());
    }
}