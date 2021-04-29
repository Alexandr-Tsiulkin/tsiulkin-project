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

    @GetMapping(value = "/user/welcome-seller/{name}")
    public String getSeller(@PathVariable String name) {
        return "welcome-seller";
    }

    @GetMapping(value = "/user/welcome-customer/{name}")
    public String getCustomer(@PathVariable String name) {
        return "welcome-customer";
    }

    @GetMapping(value = "/admin/welcome-admin/{name}")
    public String getAdmin(@PathVariable String name) {
        return "welcome-admin";
    }
}
