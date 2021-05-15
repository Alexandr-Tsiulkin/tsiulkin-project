package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class ShowUserDTO {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String roleName;
    private String password;
    private String encryptedPassword;
}
