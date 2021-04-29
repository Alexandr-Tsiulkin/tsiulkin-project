package com.gmail.alexandr.tsiulkin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping(value = "/user/welcome-seller")
    public String getSeller() {
        return "welcome-seller";
    }

    @GetMapping(value = "/user/welcome-customer")
    public String getCustomer() {
        return "welcome-customer";
    }

    @GetMapping(value = "/admin/welcome-admin")
    public String getAdmin() {
        return "welcome-admin";
    }
}
