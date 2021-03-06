package com.gmail.alexandr.tsiulkin.controller.api;

import com.gmail.alexandr.tsiulkin.config.BaseIT;
import com.gmail.alexandr.tsiulkin.service.model.AddItemDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowItemDTO;
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

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ITEMS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql({"/scripts/clean_item.sql", "/scripts/init_item.sql"})
class ItemsAPIControllerIT extends BaseIT {

    @Test
    void shouldGetAllItems() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ShowItemDTO>> response = testRestTemplate
                .withBasicAuth("rest@gmail.com", "test")
                .exchange(
                        REST_API_USER_PATH + ITEMS_PATH,
                        HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals("test title", response.getBody().get(0).getTitle());
        assertEquals(UUID.fromString("e5b0f808-ccf1-488f-ace7-2d48e50dea5c"), response.getBody().get(0).getUuid());
        assertEquals(BigDecimal.valueOf(500), response.getBody().get(0).getPrice());
    }

    @Test
    void shouldGetItemById() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<ShowItemDTO> response = testRestTemplate
                .withBasicAuth("rest@gmail.com", "test")
                .exchange(
                        REST_API_USER_PATH + ITEMS_PATH + "/1",
                        HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldAddItemAndReturnRightShowItem() {
        AddItemDTO itemDTO = new AddItemDTO();
        itemDTO.setTitle("test title");
        itemDTO.setPrice(BigDecimal.valueOf(500));
        itemDTO.setContent("test content");
        HttpEntity<AddItemDTO> httpEntity = new HttpEntity<>(itemDTO);
        ResponseEntity<AddItemDTO> response = testRestTemplate
                .withBasicAuth("rest@gmail.com", "test")
                .exchange(
                        REST_API_USER_PATH + ITEMS_PATH,
                        HttpMethod.POST,
                        httpEntity,
                        AddItemDTO.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldDeleteItemById() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<ShowItemDTO> response = testRestTemplate
                .withBasicAuth("rest@gmail.com", "test")
                .exchange(
                        REST_API_USER_PATH + ITEMS_PATH + "/1",
                        HttpMethod.DELETE,
                        request,
                        new ParameterizedTypeReference<>() {
                        }
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}