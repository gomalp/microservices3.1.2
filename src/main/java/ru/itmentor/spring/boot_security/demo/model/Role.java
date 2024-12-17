package ru.itmentor.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//GrantedAuthority сущность описывающая права юзера
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority { //сущность, описывающая права юзера в spring security
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Использование автоинкремента
    private Long id;

    private String role;
    // Вариант 1: Простая аннотация @Enumerated
   // @Enumerated(EnumType.STRING)  // Хранится как СТРОКА
   // @Column(name = "user_role")
   // private Roles role;

    //@ManyToMany(mappedBy = "roles") //указывает, что текущая сущность не является владельцем связи в отношениях "Многие ко многим".
    // Вместо этого она обозначает обратную сторону (inverse side). В этом случае связь управляется полем roles в сущности user.
   // private Set<User> users = new HashSet<>();

    public Role() {}

    public Role(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String getAuthority() {
        return "ROLE_" + this.role;
    }
}
