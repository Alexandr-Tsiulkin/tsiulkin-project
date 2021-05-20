package com.gmail.alexandr.tsiulkin.controller.api;

import com.gmail.alexandr.tsiulkin.config.BaseIT;
import com.gmail.alexandr.tsiulkin.service.UserService;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ARTICLES_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class ArticleAPIControllerIT extends BaseIT {

    @MockBean
    private UserAPIController userAPIController;
    @MockBean
    private UserService userService;

    @Test
    @Sql({"/scripts/clean.sql", "/scripts/init.sql"})
    void shouldGetAllArticles() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ShowArticleDTO>> response = testRestTemplate.exchange(
                REST_API_USER_PATH + ARTICLES_PATH,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals("2021-05-21 03:00:00", response.getBody().get(0).getDate());
        assertEquals("test title", response.getBody().get(0).getTitle());
        assertEquals("test content", response.getBody().get(0).getShortContent());
    }
}