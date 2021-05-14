package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ShowItemDTO {

    private Long id;
    private String title;
    private UUID uuid;
    private BigDecimal price;
    private String content;

}
