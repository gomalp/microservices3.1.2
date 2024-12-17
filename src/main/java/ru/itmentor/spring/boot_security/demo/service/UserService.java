package ru.itmentor.spring.boot_security.demo.service;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(Long id);
    void updateUser(User user);
    void deleteUserById(Long id);
    User getUserByUsername(String username);
    boolean existsByUserNameAndIdNot(String userName, Long id);
    boolean existsByUserName(String userName);
}