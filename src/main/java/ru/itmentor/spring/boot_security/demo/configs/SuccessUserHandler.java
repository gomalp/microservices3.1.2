package ru.itmentor.spring.boot_security.demo.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.controller.AdminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component  // для авторизованных пользователей
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    private static final Logger logger = LoggerFactory.getLogger(SuccessUserHandler.class);
    private static final Map<String, String> ROLE_PRIORITY_MAP = new LinkedHashMap<>();

    //используемые роли в порядке приоритета
    static {
        ROLE_PRIORITY_MAP.put("ROLE_ADMIN", "/admin/");
        ROLE_PRIORITY_MAP.put("ROLE_MANAGER", "/manager/");
        ROLE_PRIORITY_MAP.put("ROLE_USER", "/user/");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        // roles имеем Set ролей авторизованного пользователя
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        logger.info("--->> SuccessUserHandler:  user={}", authentication.getName());
        logger.info("--->> SuccessUserHandler:  roles={}", roles);
        // ищем самую приоритетную роль пользователя и перенаправляем на соотв URL или на index
        String redirectUrl = ROLE_PRIORITY_MAP.keySet().stream()
                    .filter(roles::contains)
                    .map(ROLE_PRIORITY_MAP::get)
                    .findFirst()
                    .orElse("/index");
        logger.info("--->> SuccessUserHandler:  redirectUrl={}", redirectUrl);
        httpServletResponse.sendRedirect(redirectUrl);

    }
}