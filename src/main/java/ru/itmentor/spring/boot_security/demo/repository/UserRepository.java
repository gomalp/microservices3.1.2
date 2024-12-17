package ru.itmentor.spring.boot_security.demo.repository;

import ru.itmentor.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    boolean existsByUserNameAndIdNot(String userName, Long id);//проверка наличие пользователя с заданным userName, но с id, отличным от указанного.
    boolean existsByUserName(String userName);
    // JpaRepository уже включает в себя:
    // - findById(Long id)
    // - save(User user)
    // - deleteById(Long id)
    // - findAll()
    // - count()
}