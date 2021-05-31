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
import org.springframework.util.StringUtils;

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
        List<User> users = userRepository.findAll(pageDTO.getStartPosition(), MAXIMUM_USERS_ON_PAGE);
        pageDTO.getUsers().addAll(users.stream()
                .map(userConverter::convert)
                .collect(Collectors.toList()));
        return pageDTO;
    }

    @Override
    @Transactional
    public ShowUserDTO persist(AddUserDTO addUserDTO) throws ServiceException {
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
                ShowUserDTO showUserDTO = userConverter.convert(user);
                showUserDTO.setPassword(randomPassword);
                return showUserDTO;
            } else {
                throw new ServiceException(String.format("User with role: %s was not found", addUserDTO.getRole().name()));
            }
        } else {
            throw new ServiceException(String.format("User with username: %s already exists", addUserDTO.getEmail()));
        }
    }

    @Override
    @Transactional
    public boolean isDeleteById(Long id) {
        userRepository.removeById(id);
        return true;
    }

    @Override
    @Transactional
    public ShowUserDTO resetPassword(Long id) throws ServiceException {
        User user = userRepository.findById(id);
        if (Objects.nonNull(user)) {
            String randomPassword = generateRandomPassword();
            String encodePassword = passwordEncoder.encode(randomPassword);
            user.setPassword(encodePassword);
            userRepository.merge(user);
            ShowUserDTO showUserDTO = userConverter.convert(user);
            showUserDTO.setPassword(randomPassword);
            return showUserDTO;
        } else {
            throw new ServiceException(String.format("User with id: %s was not found", id));
        }
    }

    @Override
    @Transactional
    public ShowUserDTO changeRoleById(String roleName, Long id) throws ServiceException {
        Role roleByRoleName = roleRepository.findByRoleName(roleName);
        if (Objects.nonNull(roleByRoleName)) {
            User user = userRepository.findById(id);
            if (Objects.nonNull(user)) {
                roleByRoleName.getUsers().add(user);
                userRepository.merge(user);
                return userConverter.convert(user);
            } else {
                throw new ServiceException(String.format("User with id: %s was not found", id));
            }
        } else {
            throw new ServiceException(String.format("User with role: %s was not found", roleName));
        }
    }

    @Override
    @Transactional
    public ShowUserDetailsDTO getUserByUserName(String userName) throws ServiceException {
        User user = userRepository.findUserByUsername(userName);
        if (Objects.nonNull(user)) {
            return userConverter.convertUserToUserDetailsDTO(user);
        } else {
            throw new ServiceException(String.format("User with username: %s was not found", userName));
        }
    }

    @Override
    @Transactional
    public ShowUserDetailsDTO changeParameterById(AddUserDetailsDTO addUserDetailsDTO) throws ServiceException {
        User user = userRepository.findById(addUserDetailsDTO.getId());
        if (Objects.nonNull(user)) {
            User changeUser = changeUserFields(addUserDetailsDTO, user);
            userRepository.merge(changeUser);
            return userConverter.convertUserToUserDetailsDTO(changeUser);
        } else {
            throw new ServiceException(String.format("User with id: %s was not found", addUserDetailsDTO.getId()));
        }
    }

    private User changeUserFields(AddUserDetailsDTO addUserDetailsDTO, User user) throws ServiceException {
        String firstName = addUserDetailsDTO.getFirstName();
        if (StringUtils.hasText(firstName)) {
            user.setFirstName(firstName);
        }
        String lastName = addUserDetailsDTO.getLastName();
        if (StringUtils.hasText(lastName)) {
            user.setLastName(lastName);
        }
        String address = addUserDetailsDTO.getAddress();
        if (StringUtils.hasText(address)) {
            UserDetails userDetails = user.getUserDetails();
            userDetails.setAddress(address);
            user.setUserDetails(userDetails);
        }
        String telephone = addUserDetailsDTO.getTelephone();
        if (StringUtils.hasText(telephone)) {
            UserDetails userDetails = user.getUserDetails();
            userDetails.setTelephone(telephone);
            user.setUserDetails(userDetails);
        }
        String oldPassword = addUserDetailsDTO.getOldPassword();
        String newPassword = addUserDetailsDTO.getNewPassword();
        if (StringUtils.hasText(oldPassword) && StringUtils.hasText(newPassword)) {
            String userPassword = user.getPassword();
            if (passwordEncoder.matches(oldPassword, userPassword)) {
                log.info(passwordEncoder.matches(oldPassword, userPassword));
                String encodePassword = passwordEncoder.encode(addUserDetailsDTO.getNewPassword());
                user.setPassword(encodePassword);
            } else {
                throw new ServiceException("The current password was entered incorrectly");
            }
        }
        return user;
    }
}
