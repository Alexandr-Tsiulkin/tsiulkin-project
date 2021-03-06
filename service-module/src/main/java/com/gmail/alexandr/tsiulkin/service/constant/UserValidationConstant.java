package com.gmail.alexandr.tsiulkin.service.constant;

public interface UserValidationConstant {

    int MAXIMUM_LAST_NAME_SIZE = 40;
    int MAXIMUM_FIRST_NAME_SIZE = 20;
    int MAXIMUM_MIDDLE_NAME_SIZE = 40;
    int MAXIMUM_EMAIL_NAME_SIZE = 50;
    int MAXIMUM_ADDRESS_SIZE = 20;
    int MAXIMUM_TELEPHONE_SIZE = 20;
    String ONLY_LATIN_LETTERS_REGEXP = "^([A-Z]{1}[a-z]{1,})$";
    String EMAIL_REGEXP = "^(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})$";
}
