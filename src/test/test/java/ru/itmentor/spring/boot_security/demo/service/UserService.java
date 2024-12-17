package ru.itmentor.spring.boot_security.demo.service;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(Long id);
    void deleteUserById(Long id);
}