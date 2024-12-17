package ru.itmentor.spring.boot_security.demo.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmentor.spring.boot_security.demo.controller.AdminController;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;
import org.springframework.stereotype.Component;


import java.sql.Date;

@Component
public class UserInitializer implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(UserInitializer.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("++++++++++++++++++++++++++++++++++ UserInitializer.run()");
        // Создание ролей, если они ещё не существуют
        Role adminRole = roleRepository.findByRole("ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ADMIN");
            roleRepository.save(adminRole);
        }

        Role userRole = roleRepository.findByRole("USER");
        if (userRole == null) {
            userRole = new Role("USER");
            roleRepository.save(userRole);
        }


        if (userRepository.count() == 0) {
            System.out.println("Инициализация таблицы users начальными данными...");

            userRepository.save(new User("admin",passwordEncoder.encode("admin"),adminRole,"Serg", "Doe", Date.valueOf("1990-01-15"), "123 Main St"));
            userRepository.save(new User("sss",passwordEncoder.encode("222"),userRole,"Jane", "Smith", Date.valueOf("1985-05-22"), "456 Oak Ave"));
            userRepository.save(new User("aaa",passwordEncoder.encode("333"),userRole,"Alice", "Johnson", Date.valueOf("1992-12-02"), "789 Pine Rd"));

            System.out.println("Начальные данные добавлены.");
        } else {
            System.out.println("Данные уже существуют. Пропускаем инициализацию.");
        }
    }
}