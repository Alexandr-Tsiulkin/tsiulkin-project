package com.gmail.alexandr.tsiulkin.controller.api;

import com.gmail.alexandr.tsiulkin.service.OrderService;
import com.gmail.alexandr.tsiulkin.service.model.ShowOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrdersAPIController {

    private final OrderService orderService;

    @GetMapping("/api/orders")
    public ResponseEntity<List<ShowOrderDTO>> getOrders() {
        List<ShowOrderDTO> orders = orderService.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
