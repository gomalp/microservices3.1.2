package ru.itmentor.spring.boot_security.demo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.*;

@Entity
@Table(name = "users") // Указывается имя таблицы в базе данных
public class User implements UserDetails {  //UserDetails будет интерпретироваться Spring Security как пользователь.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Использование автоинкремента
    private Long id;

    @NotNull(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @NotBlank(message = "Имя не может быть пустым")
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "birthDay")
    private Date birthDay;

    @Column(name = "homeAdress")
    private String homeAdress;


    // Поля, относящиеся к Spring Security
    @Column(nullable = false)
    private boolean accountNonExpired = true;

    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(nullable = false)
    private boolean enabled = true;



    @Column(name = "roles")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles", // Имя таблицы связи
            joinColumns = @JoinColumn(name = "user_id"), // Внешний ключ к таблице Users
            inverseJoinColumns = @JoinColumn(name = "role_id") // Внешний ключ к таблице Roles
    )
    @NotEmpty(message = "Пользователь должен иметь хотя бы одну роль")
    private Set<Role> roles = new HashSet<>();

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 3, message = "Пароль должен быть не меньше 3 символов")
    @Column(nullable = false)
    private String password;




    //////////////
    public User() {}

    public User(String userName, String password, Role role, String firstName, String lastName, Date birthDay, String homeAdress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.homeAdress = homeAdress;
        this.userName = userName;
        this.password=password;
        addRole(role);
    }
    ///
    public void addRole(Role role){
        roles.add(role);
    }
////////////////////
    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public String getHomeAdress() {
        return homeAdress;
    }

    public void setHomeAdress(String homeAdress) {
        this.homeAdress = homeAdress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    ///////////////////////////
    @Override
    // список ролей пользователя.
    public Set<Role> getAuthorities() {
        System.out.println("USER - getAuthorities - roles="  );
        roles.forEach(item -> System.out.println("---"+item.getRole()));
        return roles;
    }

    @Override
    public String getUsername() {
        return userName;
    }
    //Учетная запись "истекает" в результате условия, определяемого бизнес-логикой.
    // Текущая реализация всегда возвращает false (аккаунт истёк).
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // Если пользователь заблокирован (возвращается false), доступ к системе запрещён.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //Отвечает за актуальность пароля. Например, если требуется регулярно обновлять пароль, метод определяет,
    // истёк ли срок действия текущего пароля.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //Этот метод определяет, активен ли аккаунт. Например, учетную запись можно активировать после верификации email.
    @Override
    public boolean isEnabled() {
        return true;
    }


    //////////////


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", homeAdress='" + homeAdress + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", password='" + password + '\'' +
                '}';
    }
}