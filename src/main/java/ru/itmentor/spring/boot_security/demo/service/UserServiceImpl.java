package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;
import ru.itmentor.spring.boot_security.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        logger.info("getUserById: id={}, user={}", id, user);
        return user;
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByUserNameAndIdNot(String userName, Long id) {
        return userRepository.existsByUserNameAndIdNot(userName, id);
    }

    @Override
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUserName(user.getUserName());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        //existingUser.setPassword(user.getPassword());
        existingUser.setBirthDay(user.getBirthDay());
        existingUser.setHomeAdress(user.getHomeAdress());
        existingUser.setRoles(user.getRoles());
        // Хэширование пароля при обновлении
        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(existingUser);
    }
}
