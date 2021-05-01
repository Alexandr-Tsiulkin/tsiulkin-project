package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangeUserRoleDTO {

    private Long id;
    private String roleName;
}
