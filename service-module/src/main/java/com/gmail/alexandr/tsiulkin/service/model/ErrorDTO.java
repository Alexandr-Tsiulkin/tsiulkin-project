package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
public class ErrorDTO {

    private List<FieldError> errors;
}
