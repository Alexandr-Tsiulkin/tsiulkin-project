package com.gmail.alexandr.tsiulkin.controller;

import com.gmail.alexandr.tsiulkin.service.UserService;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin/users/{page}")
    public String getUsers(Model model, @PathVariable("page") int page) {
        List<ShowUserDTO> users = userService.getAllUsers(page);
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/admin/add-user")
    public String addPage(Model model) {
        model.addAttribute("user", new AddUserDTO());
        return "add-user";
    }

    @PostMapping(value = "/admin/add-user")
    public String add(@Valid AddUserDTO addUserDTO, BindingResult error) {
        logger.info("addUser:{}", addUserDTO);
        if (error.hasErrors()) {
            logger.info("errors:{}", error);
            return "add-user";
        } else {
            userService.persist(addUserDTO);
        }
        return "redirect:/admin/users/1";
    }
}
