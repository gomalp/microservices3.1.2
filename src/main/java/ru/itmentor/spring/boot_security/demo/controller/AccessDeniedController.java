package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // Название шаблона Thymeleaf (access-denied.html)
    }
}