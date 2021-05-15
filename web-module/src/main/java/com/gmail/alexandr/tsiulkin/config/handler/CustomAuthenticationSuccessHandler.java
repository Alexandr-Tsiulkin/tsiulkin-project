package com.gmail.alexandr.tsiulkin.config.handler;

import com.gmail.alexandr.tsiulkin.service.model.RoleDTOEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.gmail.alexandr.tsiulkin.constant.PathConstant.ADMIN_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.SELLER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_ADMIN_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_CUSTOMER_PATH;
import static com.gmail.alexandr.tsiulkin.constant.PathConstant.WELCOME_SELLER_PATH;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains(RoleDTOEnum.ADMINISTRATOR.name())) {
            response.sendRedirect(ADMIN_PATH + WELCOME_ADMIN_PATH);
        } else if (roles.contains(RoleDTOEnum.CUSTOMER_USER.name())) {
            response.sendRedirect(CUSTOMER_PATH + WELCOME_CUSTOMER_PATH);
        } else if (roles.contains(RoleDTOEnum.SALE_USER.name())) {
            response.sendRedirect(SELLER_PATH + WELCOME_SELLER_PATH);
        }
    }
}
