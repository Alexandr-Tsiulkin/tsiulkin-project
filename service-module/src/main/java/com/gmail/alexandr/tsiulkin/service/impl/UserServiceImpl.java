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
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import com.gmail.alexandr.tsiulkin.service.model.ShowUserDTO;
import com.gmail.alexandr.tsiulkin.service.model.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

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
    public void addUserAndSendPasswordToEmail(AddUserDTO addUserDTO) throws ServiceException {
        SimpleMailMessage message = persist(addUserDTO);
        if (Objects.nonNull(message)) {
            javaMailSender.send(message);
        }
    }

    @Override
    @Transactional
    public SimpleMailMessage persist(AddUserDTO addUserDTO) throws ServiceException {
        User userByUsername = userRepository.findUserByUsername(addUserDTO.getEmail());
        SimpleMailMessage message = new SimpleMailMessage();
        if (Objects.isNull(userByUsername)) {
            String roleName = addUserDTO.getRole().name();
            Role role = roleRepository.findByRoleName(roleName);
            log.info("role: {}", role);
            if (Objects.nonNull(role)) {
                User user = userConverter.convert(addUserDTO);
                user.setRole(role);
                String randomPassword = generateRandomPassword();
                String encodePassword = passwordEncoder.encode(randomPassword);
                user.setPassword(encodePassword);
                String email = user.getEmail();
                String recipientMail = environment.getProperty("spring.mail.username");
                message = getMailMessageForAddUser(email, randomPassword, recipientMail);
                userRepository.persist(user);
            }
        } else {
            throw new ServiceException("User with username: " + addUserDTO.getEmail() + " already exists");
        }
        return message;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.removeById(id);
    }

    @Override
    @Transactional
    public void resetPasswordAndSendToEmail(Long id) {
        SimpleMailMessage message = resetPassword(id);
        if (Objects.nonNull(message)) {
            javaMailSender.send(message);
        }
    }

    @Override
    @Transactional
    public SimpleMailMessage resetPassword(Long id) {
        User user = userRepository.findById(id);
        SimpleMailMessage message = new SimpleMailMessage();
        if (Objects.nonNull(user)) {
            String randomPassword = generateRandomPassword();
            String encodePassword = passwordEncoder.encode(randomPassword);
            user.setPassword(encodePassword);
            String firstName = user.getFirstName();
            String recipientMail = environment.getProperty("spring.mail.username");
            message = getMailMessageForResetPassword(firstName, randomPassword, recipientMail);
            userRepository.merge(user);
        }
        return message;
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
    public UserDetailsDTO getUserByUserName(String userName) {
        User user = userRepository.findUserByUsername(userName);
        return userConverter.convertUserToUserDetailsDTO(user);
    }

    @Override
    @Transactional
    public UserDetailsDTO changeNameById(UserDetailsDTO userDetailsDTO) {
        User user = userRepository.findById(userDetailsDTO.getId());
        String firstName = userDetailsDTO.getFirstName();
        user.setFirstName(firstName);
        userRepository.merge(user);
        return userConverter.convertUserToUserDetailsDTO(user);
    }

    @Override
    @Transactional
    public UserDetailsDTO changeSurnameById(UserDetailsDTO userDetailsDTO) {
        User user = userRepository.findById(userDetailsDTO.getId());
        String lastName = userDetailsDTO.getLastName();
        user.setLastName(lastName);
        userRepository.merge(user);
        return userConverter.convertUserToUserDetailsDTO(user);
    }

    @Override
    @Transactional
    public UserDetailsDTO changeAddressById(UserDetailsDTO userDetailsDTO) {
        User user = userRepository.findById(userDetailsDTO.getId());
        String address = userDetailsDTO.getAddress();
        UserDetails userDetails = user.getUserDetails();
        userDetails.setAddress(address);
        user.setUserDetails(userDetails);
        userRepository.merge(user);
        return userConverter.convertUserToUserDetailsDTO(user);
    }

    @Override
    @Transactional
    public UserDetailsDTO changeTelephoneById(UserDetailsDTO userDetailsDTO) {
        User user = userRepository.findById(userDetailsDTO.getId());
        String telephone = userDetailsDTO.getTelephone();
        UserDetails userDetails = user.getUserDetails();
        userDetails.setTelephone(telephone);
        user.setUserDetails(userDetails);
        userRepository.merge(user);
        return userConverter.convertUserToUserDetailsDTO(user);
    }

    @Override
    @Transactional
    public UserDetailsDTO changePasswordById(UserDetailsDTO userDetailsDTO) throws ServiceException {
        User user = userRepository.findById(userDetailsDTO.getId());
        String oldPassword = userDetailsDTO.getOldPassword();
        String userPassword = user.getPassword();
        if (passwordEncoder.matches(oldPassword, userPassword)) {
            String newPassword = passwordEncoder.encode(userDetailsDTO.getNewPassword());
            user.setPassword(newPassword);
            userRepository.merge(user);
        } else {
            throw new ServiceException("The current password was entered incorrectly");
        }
        return userDetailsDTO;
    }

    private SimpleMailMessage getMailMessageForAddUser(String email, String randomPassword, String recipientMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientMail);
        message.setSubject("Your registration password");
        message.setText("Hello, your account " + email + " has been successfully created:" +
                "your password: " + randomPassword);
        return message;
    }

    private SimpleMailMessage getMailMessageForResetPassword(String firstName, String randomPassword, String recipientMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientMail);
        message.setSubject("Your new password");
        message.setText("Hello " + firstName + ", your password has been successfully reset:" +
                "your new password: " + randomPassword);
        return message;
    }


}
