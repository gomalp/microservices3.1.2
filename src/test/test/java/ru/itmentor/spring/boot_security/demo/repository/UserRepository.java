package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository уже включает в себя:
    // - findById(Long id)
    // - save(User user)
    // - deleteById(Long id)
    // - findAll()
    // - count()
}