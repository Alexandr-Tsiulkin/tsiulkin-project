package com.gmail.alexandr.tsiulkin.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.alexandr.tsiulkin.service.ArticleService;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowCommentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ARTICLES_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleAPIController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@ActiveProfiles("test")
class ArticleAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetArticlesAndReturnOk() throws Exception {
        mockMvc.perform(get(REST_API_USER_PATH + ARTICLES_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatTheBusinessLogicWasCalledWhenWeRequestGEtArticles() throws Exception {
        mockMvc.perform(get(REST_API_USER_PATH + ARTICLES_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(articleService, times(1)).getArticles();
    }

    @Test
    void shouldReturnCollectionOfObjectsWhenWeRequestGEtArticles() throws Exception {
        ShowArticleDTO showArticleDTO = new ShowArticleDTO();
        showArticleDTO.setId(1L);
        showArticleDTO.setDate("18-05-2021");
        showArticleDTO.setTitle("Title");
        showArticleDTO.setFirstName("first name");
        showArticleDTO.setLastName("last name");
        showArticleDTO.setShortContent("short content");
        showArticleDTO.setFullContent("full content");
        ShowCommentDTO showCommentDTO = new ShowCommentDTO();
        showCommentDTO.setId(1L);
        showCommentDTO.setFullName("full name");
        showCommentDTO.setDate("17-05-2021");
        showCommentDTO.setFullContent("content");
        showArticleDTO.getComments().add(showCommentDTO);

        List<ShowArticleDTO> articles = Collections.singletonList(showArticleDTO);

        when(articleService.getArticles()).thenReturn(articles);

        MvcResult mvcResult = mockMvc.perform(get(REST_API_USER_PATH + ARTICLES_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualToIgnoringCase(objectMapper.writeValueAsString(articles));
    }
}
