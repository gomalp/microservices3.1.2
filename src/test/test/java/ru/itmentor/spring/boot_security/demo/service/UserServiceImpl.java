package ru.itmentor.spring.boot_security.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

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
        //Optional<User> optional = userRepository.findById(id);
        //return optional.orElse(null);
        logger.info("getUserById: id={}, user={}", id, user);
        return user;
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
}
