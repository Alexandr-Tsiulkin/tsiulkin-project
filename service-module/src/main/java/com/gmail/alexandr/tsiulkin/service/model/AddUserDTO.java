package com.gmail.alexandr.tsiulkin.service.model;

import com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddUserDTO {

    private Long id;
    @NotBlank(message = "Last name cannot be blank")
    @NotNull(message = "Last name cannot be null")
    @Size(max = UserValidationConstant.MAXIMUM_LAST_NAME_SIZE, message = "Last name cannot be more than " + UserValidationConstant.MAXIMUM_LAST_NAME_SIZE + " symbols")
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP, message = "The last name must contain only Latin letters")
    private String lastName;
    @NotBlank(message = "First name cannot be blank")
    @NotNull(message = "First name cannot be null")
    @Size(max = UserValidationConstant.MAXIMUM_FIRST_NAME_SIZE, message = "First name cannot be more than " + UserValidationConstant.MAXIMUM_FIRST_NAME_SIZE + " symbols")
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP, message = "The first name must contain only Latin letters")
    private String firstName;
    @NotBlank(message = "Middle name cannot be blank")
    @NotNull(message = "Middle name cannot be null")
    @Size(max = UserValidationConstant.MAXIMUM_MIDDLE_NAME_SIZE, message = "Middle name cannot be more than " + UserValidationConstant.MAXIMUM_MIDDLE_NAME_SIZE + " symbols")
    @Pattern(regexp = UserValidationConstant.ONLY_LATIN_LETTERS_REGEXP, message = "The middle name must contain only Latin letters")
    private String middleName;
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    @Size(max = UserValidationConstant.MAXIMUM_EMAIL_NAME_SIZE, message = "Email cannot be more than " + UserValidationConstant.MAXIMUM_EMAIL_NAME_SIZE + " symbols")
    @Pattern(regexp = UserValidationConstant.EMAIL_REGEXP, message = "Email does not match the standard template")
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleDTOEnum role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleDTOEnum getRole() {
        return role;
    }

    public void setRole(RoleDTOEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AddUserDTO{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}

