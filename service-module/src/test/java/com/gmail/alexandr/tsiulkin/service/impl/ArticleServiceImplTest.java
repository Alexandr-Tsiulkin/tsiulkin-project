package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.ArticleRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Article;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.service.converter.ArticleConverter;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.ChangeArticleDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowArticleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleConverter articleConverter;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void shouldGetArticlesByNumberPage() {
        int startPosition = 0;
        int maximumItemsOnPage = 10;
        List<Article> articles = new ArrayList<>();
        when(articleRepository.findAll(startPosition, maximumItemsOnPage)).thenReturn(articles);
        List<ShowArticleDTO> articleDTOS = articles.stream()
                .map(articleConverter::convert)
                .collect(Collectors.toList());
        PageDTO pageDTO = new PageDTO();
        pageDTO.getArticles().addAll(articleDTOS);

        PageDTO itemsByPage = articleService.getArticlesByPage(1);

        assertEquals(pageDTO.getItems(), itemsByPage.getItems());
    }

    @Test
    void shouldGetAllArticles() {
        List<Article> articlesWithMock = new ArrayList<>();
        when(articleRepository.findAll()).thenReturn(articlesWithMock);
        List<ShowArticleDTO> articlesDTOSWithMock = articlesWithMock.stream()
                .map(articleConverter::convert)
                .collect(Collectors.toList());

        List<Article> articles = articleRepository.findAll();
        List<ShowArticleDTO> articleDTOS = articles.stream()
                .map(articleConverter::convert)
                .collect(Collectors.toList());

        assertEquals(articlesDTOSWithMock, articleDTOS);
    }

    @Test
    void shouldFindArticleByIdAndReturnNullIfItemNotFound() {
        Long id = 1L;
        Article article = articleRepository.findById(id);
        assertNull(article);
    }

    @Test
    void shouldFindArticleByIdAndReturnNotNullIfItemWasFound() {
        Long id = 1L;
        ShowArticleDTO articleDTO = new ShowArticleDTO();
        articleDTO.setId(id);
        when(articleService.getArticleById(id)).thenReturn(articleDTO);

        assertNotNull(articleDTO);
    }

    @Test
    void shouldGetArticleById() {
        Long id = 1L;
        Article article = new Article();
        article.setId(id);
        when(articleRepository.findById(id)).thenReturn(article);
        ShowArticleDTO articleDTO = new ShowArticleDTO();
        articleDTO.setId(id);
        when(articleConverter.convert(article)).thenReturn(articleDTO);

        ShowArticleDTO articleById = articleService.getArticleById(id);

        assertEquals(articleDTO, articleById);
    }

    @Test
    void shouldDeleteArticleById() {
        Long id = 1L;
        boolean isDeleteArticle = articleService.isDeleteById(id);

        assertTrue(isDeleteArticle);
    }

    @Test
    void shouldAddArticleAndReturnTrueIfAddedSuccessfully() throws ServiceException {
        User user = new User();
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "test@gmail.com";
            }
        };
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.findUserByUsername(authentication.getName())).thenReturn(user);
        AddArticleDTO addArticleDTO = new AddArticleDTO();
        Article article = new Article();
        when(articleConverter.convert(addArticleDTO)).thenReturn(article);
        boolean isAddArticle = articleService.isAdd(addArticleDTO);

        assertTrue(isAddArticle);
    }

    @Test
    void shouldAddArticleAndReturnFalseIfUserWasNotAuthentication() throws ServiceException {
        SecurityContextHolder.getContext().setAuthentication(null);
        AddArticleDTO addArticleDTO = new AddArticleDTO();
        boolean isAddArticle = articleService.isAdd(addArticleDTO);

        assertFalse(isAddArticle);
    }

    @Test
    void shouldAddArticleAndReturnFalseIfUserWasNotFoundWithUserName() {
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "test@gmail.com";
            }
        };
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.findUserByUsername(authentication.getName())).thenReturn(null);
        AddArticleDTO addArticleDTO = new AddArticleDTO();
        Article article = new Article();
        when(articleConverter.convert(addArticleDTO)).thenReturn(article);

        assertThrows(ServiceException.class, () -> articleService.isAdd(addArticleDTO));
    }

    @Test
    void shouldChangeParameterByIdAndReturnNotNullObject(){
        ChangeArticleDTO changeArticleDTO = new ChangeArticleDTO();
        Long id = 1L;
        ShowArticleDTO showArticleDTO = new ShowArticleDTO();
        when(articleService.changeParameterById(changeArticleDTO,id)).thenReturn(showArticleDTO);

        assertNotNull(showArticleDTO);
    }

}