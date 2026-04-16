package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.UserRole;
import jakarta.persistence.*;

@Entity // tells Spring this is a database entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")public class User {

    // ----------------------------
    // PRIMARY KEY (REQUIRED FOR JPA)
    // ----------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // ----------------------------
    // REQUIRED BY JPA
    // Spring needs a no-args constructor to create objects from DB rows
    // ----------------------------
    public User() {
    }

    // ----------------------------
    // YOUR EXISTING CONSTRUCTOR (UNCHANGED LOGIC)
    // ----------------------------
    public User(String name, String email, String password, UserRole role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // ----------------------------
    // GETTERS / SETTERS (UNCHANGED LOGIC)
    // ----------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // ----------------------------
    // YOUR EXISTING BUSINESS LOGIC (UNCHANGED)
    // ----------------------------
    public void updateProfile(String name, String email, String password) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }
}