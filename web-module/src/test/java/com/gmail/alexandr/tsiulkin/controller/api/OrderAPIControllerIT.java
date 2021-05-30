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
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ORDERS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql({"/scripts/clean_orders.sql", "/scripts/init_orders.sql"})
class OrderAPIControllerIT extends BaseIT {

    @Test
    void shouldGetAllOrders() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ShowOrderDTO>> response = testRestTemplate
                .withBasicAuth("rest@gmail.com", "test")
                .exchange(
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

    @Test
    void shouldGetArticleById() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<ShowOrderDTO> response = testRestTemplate
                .withBasicAuth("rest@gmail.com", "test")
                .exchange(
                        REST_API_USER_PATH + ORDERS_PATH + "/1",
                        HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(500), Objects.requireNonNull(response.getBody()).getTotalPrice());
        assertEquals(UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c"), (response.getBody()).getNumberOfOrder());
    }
}