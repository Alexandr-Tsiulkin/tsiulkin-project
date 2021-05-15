package com.gmail.alexandr.tsiulkin.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ACCESS_DENIED_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ADMIN_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.LOGIN_PAGE_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.SELLER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_ADMIN_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_SELLER_PATH;

@Controller
public class LoginController {

    @GetMapping(value = LOGIN_PAGE_PATH)
    public String getLogin() {
        return "login";
    }

    @GetMapping(value = SELLER_PATH + WELCOME_SELLER_PATH)
    public String getSeller() {
        return "welcome-seller";
    }

    @GetMapping(value = CUSTOMER_PATH + WELCOME_CUSTOMER_PATH)
    public String getCustomer() {
        return "welcome-customer";
    }

    @GetMapping(value = ADMIN_PATH + WELCOME_ADMIN_PATH)
    public String getAdmin() {
        return "welcome-admin";
    }

    @GetMapping(value = ACCESS_DENIED_PATH)
    public String getAccessDeniedPage() {
        return "access-denied";
    }
}
