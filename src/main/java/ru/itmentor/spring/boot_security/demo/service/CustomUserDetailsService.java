package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден: " + username);
        }
        return user;
    }
}