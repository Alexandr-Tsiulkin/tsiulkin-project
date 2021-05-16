package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.MAXIMUM_ADDRESS_SIZE;
import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.MAXIMUM_FIRST_NAME_SIZE;
import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.MAXIMUM_LAST_NAME_SIZE;
import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.MAXIMUM_TELEPHONE_SIZE;
import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP;

@Data
public class AddUserDetailsDTO {

    private Long id;
    @NotNull
    @Size(max = MAXIMUM_FIRST_NAME_SIZE)
    private String firstName;
    @NotNull
    @Size(max = MAXIMUM_LAST_NAME_SIZE)
    private String lastName;
    @NotNull
    @Size(max = MAXIMUM_TELEPHONE_SIZE)
    private String telephone;
    @NotNull
    @Size(max = MAXIMUM_ADDRESS_SIZE)
    private String address;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
