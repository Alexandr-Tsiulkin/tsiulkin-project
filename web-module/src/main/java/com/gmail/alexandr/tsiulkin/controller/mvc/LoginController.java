package com.gmail.alexandr.tsiulkin.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping(value = "/seller/welcome-seller")
    public String getSeller() {
        return "welcome-seller";
    }

    @GetMapping(value = "/customer/welcome-customer")
    public String getCustomer() {
        return "welcome-customer";
    }

    @GetMapping(value = "/admin/welcome-admin")
    public String getAdmin() {
        return "welcome-admin";
    }

    @GetMapping(value = "/access-denied")
    public String getAccessDeniedPage() {
        return "access-denied";
    }
}