package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.*;

@Data
public class UserDetailsDTO {

    private Long id;
    @NotNull
    @NotBlank
    @Size(max = MAXIMUM_FIRST_NAME_SIZE)
    @Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP, message = "The first name must be written in Latin letters and start with a capital letter")
    private String firstName;
    @NotNull
    @NotBlank
    @Size(max = MAXIMUM_LAST_NAME_SIZE)
    @Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP, message = "The last name must be written in Latin letters and start with a capital letter")
    private String lastName;
    @NotNull
    @NotBlank
    @Size(max = MAXIMUM_TELEPHONE_SIZE)
    private String telephone;
    @NotNull
    @NotBlank
    @Size(max = MAXIMUM_ADDRESS_SIZE)
    private String address;
    @NotNull
    @NotBlank
    private String oldPassword;
    @NotNull
    @NotBlank
    private String newPassword;
}
