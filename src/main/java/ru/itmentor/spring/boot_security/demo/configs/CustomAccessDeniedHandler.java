package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final String ACCESS_DENIED_URL = "/access-denied";

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Логирование события отказа в доступе (необязательно)
        System.out.println("Доступ запрещен: " + accessDeniedException.getMessage());

        // Перенаправление на страницу отказа в доступе
        response.sendRedirect(request.getContextPath() + ACCESS_DENIED_URL);
    }
}