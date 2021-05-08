package com.gmail.alexandr.tsiulkin.controller.mvc;

import com.gmail.alexandr.tsiulkin.service.UserService;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/admin/users")
    public String getUsersByPagination(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        PageDTO pageDTO = userService.getUsersByPage(page);
        model.addAttribute("pageDTO", pageDTO);
        return "users";
    }

    @GetMapping(value = "/admin/add-user")
    public String addPage(Model model) {
        model.addAttribute("user", new AddUserDTO());
        return "add-user";
    }

    @PostMapping(value = "/admin/add-user")
    public String add(@Valid AddUserDTO addUserDTO, BindingResult error) throws ServiceException {
        log.info("addUser:{}", addUserDTO);
        if (error.hasErrors()) {
            log.info("errors:{}", error);
            return "add-user";
        } else {
            userService.persist(addUserDTO);
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

    @GetMapping(value = "/admin/reset-password/{id}")
    public String resetPasswordById(@PathVariable Long id) {
        userService.resetPassword(id);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/change-role/{id}")
    public String changeRole(@RequestParam("roleName") String roleName,
                             @PathVariable Long id) {
        userService.changeRoleById(roleName, id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/customer/user-profile")
    public String getUserProfile(Model model,
                                 @RequestParam(value = "username") String userName) {
        UserDetailsDTO userDetailsDTO = userService.getUserByUserName(userName);
        model.addAttribute("userDetailsDTO", userDetailsDTO);
        return "user-profile";
    }

    @PostMapping(value = "/customer/change-name")
    public String changeNameById(@Valid UserDetailsDTO userDetailsDTO, BindingResult result) {
        log.info("userDetailsDTO: {}", userDetailsDTO);
        if (result.hasErrors()) {
            log.info("errors:{}", result);
            return "user-profile";
        }
        log.info("userDetailsDTO: {}", userDetailsDTO.getId());
        log.info("newFirstName: {}", userDetailsDTO.getFirstName());
        userService.changeNameById(userDetailsDTO);
        return "redirect:/customer/welcome-customer";
    }

    @PostMapping(value = "/customer/change-surname")
    public String changeSurnameById(@Valid UserDetailsDTO userDetailsDTO, BindingResult result) {
        log.info("userDetailsDTO: {}", userDetailsDTO);
        if (result.hasErrors()) {
            log.info("errors:{}", result);
            return "user-profile";
        }
        log.info("id: {}", userDetailsDTO.getId());
        log.info("newFirstName: {}", userDetailsDTO.getLastName());
        userService.changeSurnameById(userDetailsDTO);
        return "redirect:/customer/welcome-customer";
    }

    @PostMapping(value = "/customer/change-address")
    public String changeAddressById(@Valid UserDetailsDTO userDetailsDTO, BindingResult result) {
        log.info("userDetailsDTO: {}", userDetailsDTO);
        if (result.hasErrors()) {
            log.info("errors:{}", result);
            return "user-profile";
        }
        log.info("id: {}", userDetailsDTO.getId());
        log.info("newAddress: {}", userDetailsDTO.getAddress());
        userService.changeAddressById(userDetailsDTO);
        return "redirect:/customer/welcome-customer";
    }

    @PostMapping(value = "/customer/change-telephone")
    public String changeTelephoneById(@Valid UserDetailsDTO userDetailsDTO, BindingResult result) {
        log.info("userDetailsDTO: {}", userDetailsDTO);
        if (result.hasErrors()) {
            log.info("errors:{}", result);
            return "user-profile";
        }
        log.info("id: {}", userDetailsDTO.getId());
        log.info("newTelephone: {}", userDetailsDTO.getTelephone());
        userService.changeTelephoneById(userDetailsDTO);
        return "redirect:/customer/welcome-customer";
    }

    @PostMapping(value = "/customer/change-password")
    public String changePasswordById(@Valid UserDetailsDTO userDetailsDTO, BindingResult result) throws ServiceException {
        log.info("userDetailsDTO: {}", userDetailsDTO);
        if (result.hasErrors()) {
            log.info("errors:{}", result);
            return "user-profile";
        }
        log.info("id: {}", userDetailsDTO.getId());
        log.info("oldPassword: {}", userDetailsDTO.getOldPassword());
        log.info("newPassword: {}", userDetailsDTO.getNewPassword());
        userService.changePasswordById(userDetailsDTO);
        return "redirect:/customer/welcome-customer";
    }
}
