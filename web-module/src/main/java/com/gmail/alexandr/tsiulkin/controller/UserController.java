package com.gmail.alexandr.tsiulkin.controller;

import com.gmail.alexandr.tsiulkin.service.PageService;
import com.gmail.alexandr.tsiulkin.service.UserService;
import com.gmail.alexandr.tsiulkin.service.model.*;
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

import static com.gmail.alexandr.tsiulkin.service.constant.UserPaginateConstant.MAXIMUM_USERS_ON_PAGE;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserService userService;
    private final PageService pageService;

    public UserController(UserService userService,
                          PageService pageService) {
        this.userService = userService;
        this.pageService = pageService;
    }

    @GetMapping(value = "/admin/users/{page}")
    public String getUsers(Model model, @PathVariable("page") int page) {
        Long countUsers = userService.getCountUsers();
        logger.info("countUsers: {}", countUsers);
        PageDTO pageDTO = pageService.getPage(countUsers, MAXIMUM_USERS_ON_PAGE, page);
        logger.info("count of pages: {}", pageDTO.getCountOfPages());
        logger.info("end page: {}", pageDTO.getEndPage());
        model.addAttribute("page", pageDTO);
        List<ShowUserDTO> users = userService.getAllUsers(page);
        model.addAttribute("users", users);
        model.addAttribute("changeRoleUser", new ChangeUserRoleDTO());
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

    @PostMapping(value = "/admin/delete")
    public String deleteUsers(@Valid DeleteUserDTO user, BindingResult result) {
        List<Long> ids = user.getIds();
        logger.info("ids: {}", ids);
        if (!result.hasErrors()) {
            for (Long id : ids) {
                userService.deleteById(id);
            }
        }
        return "redirect:/admin/users/1";
    }

    @GetMapping(value = "admin/reset-password/{id}")
    public String resetPasswordById(@PathVariable Long id) {
        userService.resetPassword(id);
        return "redirect:/admin/users/1";
    }

    @PostMapping(value = "/admin/change-role/{id}")
    public String changeRole(@Valid ChangeUserRoleDTO changeUserRoleDTO, BindingResult result) {
        String roleName = changeUserRoleDTO.getRoleName();
        logger.info("role name: {}", roleName);
        Long id = changeUserRoleDTO.getId();
        logger.info("id: {}", id);
        if (!result.hasErrors()) {
            userService.changeRoleById(changeUserRoleDTO);
        }
        return "redirect:/admin/users/1";
    }
}
