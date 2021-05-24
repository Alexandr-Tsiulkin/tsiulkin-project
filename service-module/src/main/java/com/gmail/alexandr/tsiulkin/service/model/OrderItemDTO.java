package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderItemDTO {

    @NotNull
    private Long numberOfItems;
}
