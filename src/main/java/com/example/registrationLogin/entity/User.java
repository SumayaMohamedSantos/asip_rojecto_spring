package com.example.registrationLogin.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users") // Optional: Specifies the table name in the database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private LocalDate dob;

    @ElementCollection
    @CollectionTable(name = "user_technologies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "technology")
    private List<String> technology;

    @Transient // Not persisted in the database
    private String technologyInput;

    // Constructors

    public User() {
    }

    public User(Integer userId, String name, String username, String password, LocalDate dob, List<String> technology) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.technology = technology;
    }

    // Getters and Setters

    public Integer getUserId() {
        return userId;
    }

    // No setter for userId since it's auto-generated

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Custom setter for password to hash it before storing
    public void setPassword(String password) {
        // Hash the password using BCrypt
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Getter for password (optional, be cautious with exposing passwords)
    public String getPassword() {
        return this.password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<String> getTechnology() {
        return technology;
    }

    public void setTechnology(List<String> technology) {
        this.technology = technology;
    }

    // Getter and Setter for technologyInput

    public String getTechnologyInput() {
        return technologyInput;
    }

    public void setTechnologyInput(String technologyInput) {
        this.technologyInput = technologyInput;
    }

    // Override toString(), excluding sensitive fields
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", dob=" + dob +
                ", technology=" + technology +
                '}';
    }
}
