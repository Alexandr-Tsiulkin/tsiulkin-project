package com.gmail.alexandr.tsiulkin.service.model;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.*;

@Data
public class AddUserDTO {

    private Long id;
    @NotBlank()
    @NotNull()
    @Size(max = MAXIMUM_LAST_NAME_SIZE)
    @Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP)
    private String lastName;
    @NotBlank()
    @NotNull()
    @Size(max = MAXIMUM_FIRST_NAME_SIZE)
    @Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP)
    private String firstName;
    @NotBlank()
    @NotNull()
    @Size(max = MAXIMUM_MIDDLE_NAME_SIZE)
    @Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP)
    private String middleName;
    @NotBlank()
    @NotNull()
    @Size(max = MAXIMUM_EMAIL_NAME_SIZE)
    @Pattern(regexp = EMAIL_REGEXP)
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleDTOEnum role;
}

