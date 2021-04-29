package com.gmail.alexandr.tsiulkin.service.model;

import com.gmail.alexandr.tsiulkin.repository.model.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.gmail.alexandr.tsiulkin.service.constant.UserValidationConstant.*;

public class AddUserDTO {

    private Long id;
    @NotBlank(message = "Last name cannot be blank")
    @NotNull(message = "Last name cannot be null")
    @Size(max = MAXIMUM_LAST_NAME_SIZE, message = "Last name cannot be more than " + MAXIMUM_LAST_NAME_SIZE + " symbols")
    @Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP, message = "The last name must contain only Latin letters")
    private String lastName;
    @NotBlank(message = "First name cannot be blank")
    @NotNull(message = "First name cannot be null")
    @Size(max = MAXIMUM_FIRST_NAME_SIZE, message = "First name cannot be more than " + MAXIMUM_FIRST_NAME_SIZE + " symbols")
    @Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP, message = "The first name must contain only Latin letters")
    private String firstName;
    @NotBlank(message = "Middle name cannot be blank")
    @NotNull(message = "Middle name cannot be null")
    @Size(max = MAXIMUM_MIDDLE_NAME_SIZE, message = "Middle name cannot be more than " + MAXIMUM_MIDDLE_NAME_SIZE + " symbols")
    @Pattern(regexp = ONLY_LATIN_LETTERS_REGEXP, message = "The middle name must contain only Latin letters")
    private String middleName;
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    @Size(max = MAXIMUM_EMAIL_NAME_SIZE, message = "Email cannot be more than " + MAXIMUM_EMAIL_NAME_SIZE + " symbols")
    @Pattern(regexp = EMAIL_REGEXP, message = "Email does not match the standard template")
    private String email;
/*    @NotBlank(message = "Role cannot be blank")
    @NotNull(message = "Role cannot be null")*/
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

