package com.gmail.alexandr.tsiulkin.security;

import com.gmail.alexandr.tsiulkin.config.UserDetailsServiceConfig;
import com.gmail.alexandr.tsiulkin.controller.mvc.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ADMIN_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.SELLER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_ADMIN_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_SELLER_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@Import(UserDetailsServiceConfig.class)
public class AuthorizationSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@gmail.com", authorities = "ADMINISTRATOR", password = "test")
    void shouldUserWithAdminRoleAndCorrectUserNameAndPasswordHasAccessToApp() throws Exception {
        mockMvc.perform(
                get(ADMIN_PATH + WELCOME_ADMIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "seller@gmail.com", authorities = "SALE_USER", password = "test")
    void shouldUserWithSaleRoleAndCorrectUserNameAndPasswordHasAccessToApp() throws Exception {
        mockMvc.perform(
                get(SELLER_PATH + WELCOME_SELLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "customer@gmail.com", authorities = "CUSTOMER_USER", password = "test")
    void shouldUserWithCustomerRoleAndCorrectUserNameAndPasswordHasAccessToApp() throws Exception {
        mockMvc.perform(
                get(CUSTOMER_PATH + WELCOME_CUSTOMER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
