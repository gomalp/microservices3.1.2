package ru.itmentor.spring.boot_security.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users") // Указывается имя таблицы в базе данных
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Использование автоинкремента
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "birthDay")
    private Date birthDay;

    @Column(name = "homeAdress")
    private String homeAdress;

    public User() {}

    public User(String firstName, String lastName, Date birthDay, String homeAdress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.homeAdress = homeAdress;
    }

    // Геттеры и сеттеры (необходимо добавить для JPA)
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", homeAdress='" + homeAdress + '\'' +
                '}';
    }

    public String getHomeAdress() {
        return homeAdress;
    }

    public void setHomeAdress(String homeAdress) {
        this.homeAdress = homeAdress;
    }
}