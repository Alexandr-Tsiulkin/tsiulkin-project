package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShowUserDTO {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String roleName;
}
