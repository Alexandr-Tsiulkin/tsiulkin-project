package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

@Data
public class ShowUserDetailsDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String telephone;
    private String address;
    private String oldPassword;
    private String newPassword;
}
