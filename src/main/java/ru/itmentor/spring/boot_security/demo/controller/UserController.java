package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Доступ для USER и ADMIN
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Отображение профиля текущего пользователя
    @GetMapping()
    public String showUserProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "user/profile"; // Шаблон для отображения профиля пользователя
    }
}