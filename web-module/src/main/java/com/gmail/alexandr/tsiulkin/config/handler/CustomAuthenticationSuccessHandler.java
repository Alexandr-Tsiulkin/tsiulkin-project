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

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains(RoleDTOEnum.ADMINISTRATOR.name())) {
            response.sendRedirect("/admin/welcome-admin");
        } else if (roles.contains(RoleDTOEnum.CUSTOMER_USER.name())) {
            response.sendRedirect("/customer/welcome-customer");
        } else if (roles.contains(RoleDTOEnum.SALE_USER.name())) {
            response.sendRedirect("/seller/welcome-seller");
        }
    }
}
