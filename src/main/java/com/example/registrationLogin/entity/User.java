package com.example.registrationLogin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Setter
    private String name;

    @Setter
    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Setter
    private LocalDate dob;


    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
