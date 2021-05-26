package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ShowOrderDTO {

    private Long id;
    private UUID numberOfOrder;
    private String orderStatus;
    private String title;
    private String lastName;
    private String telephone;
    private Long numberOfItems;
    private BigDecimal totalPrice;

}
