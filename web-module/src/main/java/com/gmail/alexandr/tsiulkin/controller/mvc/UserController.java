package com.gmail.alexandr.tsiulkin.controller.mvc;

import com.gmail.alexandr.tsiulkin.service.MailService;
import com.gmail.alexandr.tsiulkin.service.UserService;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDetailsDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ADMIN_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.USERS_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.USER_ADD_PATH;
import static com.gmail.alexandr.tsiulkin.service.util.SecurityUtil.getAuthentication;

@Log4j2
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping(value = ADMIN_PATH + USERS_PATH)
    public String getUsersByPagination(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        PageDTO pageDTO = userService.getUsersByPage(page);
        model.addAttribute("pageDTO", pageDTO);
        return "users";
    }

    @GetMapping(value = ADMIN_PATH + USER_ADD_PATH)
    public String addPage(Model model) {
        model.addAttribute("user", new AddUserDTO());
        return "add-user";
    }

    @PostMapping(value = ADMIN_PATH + USER_ADD_PATH)
    public String add(@Valid AddUserDTO addUserDTO, BindingResult error) throws ServiceException {
        log.info("addUser:{}", addUserDTO);
        if (error.hasErrors()) {
            log.info("errors:{}", error);
            return "add-user";
        } else {
            ShowUserDTO userDTO = userService.persist(addUserDTO);
            mailService.sendPasswordToEmailAfterAddUser(userDTO);
        }
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/users/delete")
    public String deleteUsers(@RequestParam("checkedIds") List<Long> checkedIds) {
        log.info("checkedIds: {}", checkedIds);
        for (Long id : checkedIds) {
            userService.deleteById(id);
        }
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/admin/users/{id}/reset-password")
    public String resetPasswordById(@PathVariable Long id) {
        ShowUserDTO userDTO = userService.resetPassword(id);
        mailService.sendPasswordToEmailAfterResetPassword(userDTO);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/users/{id}/change-role")
    public String changeRole(@RequestParam("roleName") String roleName,
                             @PathVariable Long id) {
        userService.changeRoleById(roleName, id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/customer/user-profile")
    public String getUserProfile(Model model) {
        Authentication authentication = getAuthentication();
        String userName = authentication.getName();
        ShowUserDetailsDTO showUserDetailsDTO = userService.getUserByUserName(userName);
        model.addAttribute("userDetails", showUserDetailsDTO);
        model.addAttribute("addUserDetails", new AddUserDetailsDTO());
        return "user-profile";
    }

    @PostMapping(value = "/customer/users/{id}/change-parameter")
    public String changeParameterById(@Valid AddUserDetailsDTO addUserDetailsDTO,
                                      BindingResult result,
                                      @PathVariable Long id,
                                      Model model) throws ServiceException {
        if (result.hasErrors()) {
            ShowUserDetailsDTO showUserDetailsDTO = userService.getUserById(id);
            model.addAttribute("userDetails", showUserDetailsDTO);
            model.addAttribute("addUserDetails", new AddUserDetailsDTO());
            return "user-profile";
        }
        userService.changeParameterById(addUserDetailsDTO, id);
        return "redirect:/customer/user-profile";
    }
}
