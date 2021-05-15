package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import javax.validation.constraints.Size;

import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.MAXIMUM_ADDRESS_SIZE;
import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.MAXIMUM_FIRST_NAME_SIZE;
import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.MAXIMUM_LAST_NAME_SIZE;
import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.MAXIMUM_TELEPHONE_SIZE;

@Data
public class AddUserDetailsDTO {

    private Long id;
    @Size(max = MAXIMUM_FIRST_NAME_SIZE)
    //@Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP, message = "The first name must be written in Latin letters and start with a capital letter")
    private String firstName;
    @Size(max = MAXIMUM_LAST_NAME_SIZE)
    //@Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP, message = "The last name must be written in Latin letters and start with a capital letter")
    private String lastName;
    @Size(max = MAXIMUM_TELEPHONE_SIZE)
    private String telephone;
    @Size(max = MAXIMUM_ADDRESS_SIZE)
    private String address;
    private String oldPassword;
    private String newPassword;
}
