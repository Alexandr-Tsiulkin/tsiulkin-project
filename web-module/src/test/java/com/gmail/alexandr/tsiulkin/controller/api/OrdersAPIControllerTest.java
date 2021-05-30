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

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ITEMS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ORDERS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.REST_API_USER_PATH;
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
        mockMvc.perform(get(REST_API_USER_PATH + ORDERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldGetEmptyListOrdersWhenWeGetOrders() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(REST_API_USER_PATH + ORDERS_PATH)
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
        showOrderDTO.setNumberOfItems(numberOfOrder);

        when(orderService.getOrders()).thenReturn(Collections.singletonList(showOrderDTO));

        MvcResult mvcResult = mockMvc.perform(get(REST_API_USER_PATH + ORDERS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertThat(contentAsString).isEqualToIgnoringCase(objectMapper.writeValueAsString(Collections.singletonList(showOrderDTO)));
    }

    @Test
    void shouldReturnItemWhenWeRequestGEtOrderById() throws Exception {
        ShowOrderDTO showOrderDTO = new ShowOrderDTO();
        Long id = 1L;
        showOrderDTO.setId(id);

        when(orderService.getOrderById(id)).thenReturn(showOrderDTO);

        MvcResult mvcResult = mockMvc.perform(get(String.format("%s%s/%s", REST_API_USER_PATH, ORDERS_PATH, id))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualToIgnoringCase(objectMapper.writeValueAsString(showOrderDTO));
    }

    @Test
    void should404WhenWeRequestGEtOrderByWrongId() throws Exception {
        ShowOrderDTO showOrderDTO = new ShowOrderDTO();
        Long id = 1L;
        showOrderDTO.setId(id);

        when(orderService.getOrderById(id)).thenReturn(showOrderDTO);
        Long wrongId = 2L;

        mockMvc.perform(get(String.format("%s%s/%s", REST_API_USER_PATH, ITEMS_PATH, wrongId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
