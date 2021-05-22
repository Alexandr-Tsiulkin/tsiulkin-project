package com.gmail.alexandr.tsiulkin.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.alexandr.tsiulkin.service.OrderService;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrdersAPIController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@ActiveProfiles("test")
public class OrdersAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldGetOkStatusWhenWeGetOrders() throws Exception {
        mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldGetEmptyListOrdersWhenWeGetOrders() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringCase(objectMapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    void shouldGetListOrdersWhenWeGetOrders() throws Exception {
        ShowOrderDTO showOrderDTO = new ShowOrderDTO();
        Long id = 1L;
        showOrderDTO.setId(id);
        Long numberOfOrder = 1L;
        showOrderDTO.setNumberOfOrder(numberOfOrder);

        when(orderService.getOrders()).thenReturn(Collections.singletonList(showOrderDTO));

        MvcResult mvcResult = mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringCase(objectMapper.writeValueAsString(Collections.singletonList(showOrderDTO)));
    }
}
