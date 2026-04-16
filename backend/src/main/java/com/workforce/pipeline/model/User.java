package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.UserRole;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    // ----------------------------
    // PRIMARY KEY
    // Auto-generated unique ID for each user in DB
    // ----------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ----------------------------
    // BASIC USER INFO
    // ----------------------------
    private String name;
    private String email;
    private String password;

    // ----------------------------
    // ROLE FIELD
    // Determines user type:
    // JOB_SEEKER, EMPLOYER, TRAINING_PROVIDER
    // ----------------------------
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // ----------------------------
    // SKILLS RELATIONSHIP
    // Many-to-Many:
    // One user can have many skills
    // One skill can belong to many users
    //
    // This is mainly used when role = JOB_SEEKER
    // ----------------------------
    @ManyToMany
    @JoinTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();

    // ----------------------------
    // REQUIRED EMPTY CONSTRUCTOR
    // Used by JPA to create objects from database rows
    // ----------------------------
    public User() {}

    // ----------------------------
    // MAIN CONSTRUCTOR
    // Used when creating users manually (e.g., Postman requests)
    // ----------------------------
    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ----------------------------
    // ADD A SKILL TO USER
    // Used for JOB_SEEKER users
    // ----------------------------
    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    // ----------------------------
    // REMOVE A SKILL FROM USER
    // ----------------------------
    public void removeSkill(Skill skill) {
        this.skills.remove(skill);
    }

    // ----------------------------
    // GET ALL SKILLS
    // Returns list of skills associated with user
    // ----------------------------
    public List<Skill> getSkills() {
        return skills;
    }

    // ----------------------------
    // GETTERS AND SETTERS
    // Standard access methods for JPA + controllers
    // ----------------------------
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}