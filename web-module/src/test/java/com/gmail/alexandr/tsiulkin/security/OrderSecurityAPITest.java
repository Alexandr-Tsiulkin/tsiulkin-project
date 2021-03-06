package com.gmail.alexandr.tsiulkin.security;


import com.gmail.alexandr.tsiulkin.config.TestUserDetailsServiceConfig;
import com.gmail.alexandr.tsiulkin.controller.api.OrdersAPIController;
import com.gmail.alexandr.tsiulkin.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ORDERS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("security")
@WebMvcTest(controllers = OrdersAPIController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@Import(TestUserDetailsServiceConfig.class)
public class OrderSecurityAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldUserWithRoleRestAPIHasAccessToGetOrders() throws Exception {
        mockMvc.perform(
                get(REST_API_USER_PATH + ORDERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("rest@gmail.com", "test"))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldUserWithAdminRoleHasNotAccessDeniedToGetOrders() throws Exception {
        mockMvc.perform(
                get(REST_API_USER_PATH + ORDERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin@gmail.com", "test"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void shouldUserWithSellerRoleHasNotAccessDeniedToGetOrders() throws Exception {
        mockMvc.perform(
                get(REST_API_USER_PATH + ORDERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("seller@gmail.com", "test"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void shouldUserWithCustomerRoleHasNotAccessDeniedToGetOrders() throws Exception {
        mockMvc.perform(
                get(REST_API_USER_PATH + ORDERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("customer@gmail.com", "test"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void shouldUserWithRoleRestAPIHasAccessToGetOrderById() throws Exception {
        mockMvc.perform(
                get(String.format("%s%s/%d", REST_API_USER_PATH, ORDERS_PATH, 1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("rest@gmail.com", "test"))
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldUserWithAdminRoleHasNotAccessDeniedToGetOrderById() throws Exception {
        mockMvc.perform(
                get(String.format("%s%s/%d", REST_API_USER_PATH, ORDERS_PATH, 1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin@gmail.com", "test"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void shouldUserWithSellerRoleHasNotAccessDeniedToGetOrderById() throws Exception {
        mockMvc.perform(
                get(String.format("%s%s/%d", REST_API_USER_PATH, ORDERS_PATH, 1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("seller@gmail.com", "test"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void shouldUserWithCustomerRoleHasNotAccessDeniedToGetOrderById() throws Exception {
        mockMvc.perform(
                get(String.format("%s%s/%d", REST_API_USER_PATH, ORDERS_PATH, 1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("customer@gmail.com", "test"))
        ).andExpect(status().isForbidden());
    }
}
