package com.gmail.alexandr.tsiulkin.service.impl;

import com.gmail.alexandr.tsiulkin.repository.RoleRepository;
import com.gmail.alexandr.tsiulkin.repository.UserRepository;
import com.gmail.alexandr.tsiulkin.repository.model.Role;
import com.gmail.alexandr.tsiulkin.repository.model.User;
import com.gmail.alexandr.tsiulkin.repository.model.UserDetails;
import com.gmail.alexandr.tsiulkin.service.UserService;
import com.gmail.alexandr.tsiulkin.service.converter.UserConverter;
import com.gmail.alexandr.tsiulkin.service.exception.ServiceException;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.AddUserDetailsDTO;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmail.alexandr.tsiulkin.service.constant.UserPaginateConstant.MAXIMUM_USERS_ON_PAGE;
import static com.gmail.alexandr.tsiulkin.service.util.ServiceUtil.generateRandomPassword;
import static com.gmail.alexandr.tsiulkin.service.util.ServiceUtil.getPageDTO;

@RequiredArgsConstructor
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PageDTO getUsersByPage(Integer page) {
        Long countUsers = userRepository.getCountUsers();
        PageDTO pageDTO = getPageDTO(page, countUsers, MAXIMUM_USERS_ON_PAGE);
        log.info("PageDTO: {}", pageDTO);
        List<User> users = userRepository.findAll(pageDTO.getStartPosition(), MAXIMUM_USERS_ON_PAGE);
        pageDTO.getUsers().addAll(users.stream()
                .map(userConverter::convert)
                .collect(Collectors.toList()));
        return pageDTO;
    }

    @Override
    @Transactional
    public ShowUserDTO persist(AddUserDTO addUserDTO) throws ServiceException {
        ShowUserDTO showUserDTO = new ShowUserDTO();
        User userByUsername = userRepository.findUserByUsername(addUserDTO.getEmail());
        if (Objects.isNull(userByUsername)) {
            String roleName = addUserDTO.getRole().name();
            Role role = roleRepository.findByRoleName(roleName);
            if (Objects.nonNull(role)) {
                User user = userConverter.convert(addUserDTO);
                user.setRole(role);
                String randomPassword = generateRandomPassword();
                String encodePassword = passwordEncoder.encode(randomPassword);
                user.setPassword(encodePassword);
                userRepository.persist(user);
                showUserDTO = userConverter.convert(user);
                showUserDTO.setPassword(randomPassword);
            }
        } else {
            throw new ServiceException("User with username: " + addUserDTO.getEmail() + " already exists");
        }
        return showUserDTO;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.removeById(id);
    }

    @Override
    @Transactional
    public ShowUserDTO resetPassword(Long id) {
        User user = userRepository.findById(id);
        ShowUserDTO showUserDTO = new ShowUserDTO();
        if (Objects.nonNull(user)) {
            String randomPassword = generateRandomPassword();
            String encodePassword = passwordEncoder.encode(randomPassword);
            user.setPassword(encodePassword);
            userRepository.merge(user);
            showUserDTO = userConverter.convert(user);
            showUserDTO.setPassword(randomPassword);
        }
        return showUserDTO;
    }

    @Override
    @Transactional
    public ShowUserDTO changeRoleById(String roleName, Long id) {
        log.info("new Role: {}", roleName);
        Role byRoleName = roleRepository.findByRoleName(roleName);
        log.info("byRoleName: {}", byRoleName);
        User user = userRepository.findById(id);
        log.info("user: {}", user);
        byRoleName.getUsers().add(user);
        userRepository.merge(user);
        return userConverter.convert(user);
    }

    @Override
    @Transactional
    public ShowUserDetailsDTO getUserByUserName(String userName) {
        User user = userRepository.findUserByUsername(userName);
        return userConverter.convertUserToUserDetailsDTO(user);
    }

    @Override
    @Transactional
    public void changeParameterById(AddUserDetailsDTO addUserDetailsDTO, Long id) throws ServiceException {
        User user = userRepository.findById(addUserDetailsDTO.getId());
        User changeUser = changeUserFields(addUserDetailsDTO, user);
        userRepository.merge(changeUser);
    }

    @Override
    @Transactional
    public ShowUserDetailsDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        return userConverter.convertUserToUserDetailsDTO(user);
    }

    private User changeUserFields(AddUserDetailsDTO addUserDetailsDTO, User user) throws ServiceException {
        if (!addUserDetailsDTO.getFirstName().isBlank()) {
            String firstName = addUserDetailsDTO.getFirstName();
            user.setFirstName(firstName);
        }
        if (!addUserDetailsDTO.getLastName().isBlank()) {
            String lastName = addUserDetailsDTO.getLastName();
            user.setLastName(lastName);
        }
        if (!addUserDetailsDTO.getAddress().isBlank()) {
            String address = addUserDetailsDTO.getAddress();
            UserDetails userDetails = user.getUserDetails();
            userDetails.setAddress(address);
            user.setUserDetails(userDetails);
        }
        if (!addUserDetailsDTO.getTelephone().isBlank()) {
            String telephone = addUserDetailsDTO.getTelephone();
            UserDetails userDetails = user.getUserDetails();
            userDetails.setTelephone(telephone);
            user.setUserDetails(userDetails);
        }
        if (!addUserDetailsDTO.getFirstName().isBlank()) {
            String firstName = addUserDetailsDTO.getFirstName();
            user.setFirstName(firstName);
        }
        if (!addUserDetailsDTO.getOldPassword().isBlank() && !addUserDetailsDTO.getNewPassword().isBlank()) {
            String oldPassword = addUserDetailsDTO.getOldPassword();
            String userPassword = user.getPassword();
            if (passwordEncoder.matches(oldPassword, userPassword)) {
                String newPassword = passwordEncoder.encode(addUserDetailsDTO.getNewPassword());
                user.setPassword(newPassword);
            } else {
                throw new ServiceException("The current password was entered incorrectly");
            }
        }
        return user;
    }
}
