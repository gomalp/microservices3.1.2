package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.sql.Date;

@Component
public class UserInitializer {

    private final UserRepository userRepository;

    public UserInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Transactional
    public void initializeUsers() {
        System.out.println("++++++++++++++++++++++++++++++++++ initializeUsers");
        if (userRepository.count() == 0) {
            System.out.println("Инициализация таблицы users начальными данными...");

            userRepository.save(new User("John", "Doe", Date.valueOf("1990-01-15"), "123 Main St"));
            userRepository.save(new User("Jane", "Smith", Date.valueOf("1985-05-22"), "456 Oak Ave"));
            userRepository.save(new User("Alice", "Johnson", Date.valueOf("1992-12-02"), "789 Pine Rd"));

            System.out.println("Начальные данные добавлены.");
        } else {
            System.out.println("Данные уже существуют. Пропускаем инициализацию.");
        }
    }
}