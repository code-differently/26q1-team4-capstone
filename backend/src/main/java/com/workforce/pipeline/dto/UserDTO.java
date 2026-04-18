package com.workforce.pipeline.dto;

import com.workforce.pipeline.model.Skill;

import java.util.List;

public class UserDTO {

    private Integer id;
    private String name;
    private String email;

    // keep simple string role for API
    private String role;

    // flattened skill names (IMPORTANT for avoiding Hibernate issues)
    private List<Skill> skills;

    // optional for creation only (NOT returned in responses ideally)
    private String password;

    public UserDTO() {}

    // ---------------- GETTERS / SETTERS ----------------

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}