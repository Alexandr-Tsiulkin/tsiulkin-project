package com.gmail.alexandr.tsiulkin.service.model;

import com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class AddUserDTO {

    private Long id;
    @NotBlank(message = "Last name cannot be blank")
    @NotNull(message = "Last name cannot be null")
    @Size(max = UserValidationConstant.MAXIMUM_LAST_NAME_SIZE)
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP)
    private String lastName;
    @NotBlank(message = "First name cannot be blank")
    @NotNull(message = "First name cannot be null")
    @Size(max = UserValidationConstant.MAXIMUM_FIRST_NAME_SIZE)
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP)
    private String firstName;
    @NotBlank(message = "Middle name cannot be blank")
    @NotNull(message = "Middle name cannot be null")
    @Size(max = UserValidationConstant.MAXIMUM_MIDDLE_NAME_SIZE)
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP)
    private String middleName;
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    @Size(max = UserValidationConstant.MAXIMUM_EMAIL_NAME_SIZE)
    @Pattern(regexp = UserValidationConstant.EMAIL_REGEXP)
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleDTOEnum role;
}

