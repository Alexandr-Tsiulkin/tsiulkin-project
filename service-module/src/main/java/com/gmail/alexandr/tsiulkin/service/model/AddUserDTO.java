package com.gmail.alexandr.tsiulkin.service.model;

import com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AddUserDTO {

    private Long id;
    @NotBlank()
    @NotNull()
    @Size(max = UserValidationConstant.MAXIMUM_LAST_NAME_SIZE)
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP)
    private String lastName;
    @NotBlank()
    @NotNull()
    @Size(max = UserValidationConstant.MAXIMUM_FIRST_NAME_SIZE)
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP)
    private String firstName;
    @NotBlank()
    @NotNull()
    @Size(max = UserValidationConstant.MAXIMUM_MIDDLE_NAME_SIZE)
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP)
    private String middleName;
    @NotBlank()
    @NotNull()
    @Size(max = UserValidationConstant.MAXIMUM_EMAIL_NAME_SIZE)
    @Pattern(regexp = UserValidationConstant.EMAIL_REGEXP)
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleDTOEnum role;
}

