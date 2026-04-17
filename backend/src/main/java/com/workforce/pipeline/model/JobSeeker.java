package com.workforce.pipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.workforce.pipeline.enums.UserRole;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class JobSeeker extends User {

    // -----------------------------------
    // SKILLS (Many-to-Many relationship)
    // -----------------------------------
    @ManyToMany
    @JoinTable(
            name = "jobseeker_skills",
            joinColumns = @JoinColumn(name = "jobseeker_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonIgnoreProperties({"jobs", "trainingPrograms"})
    private List<Skill> skills = new ArrayList<>();

    // -----------------------------------
    // REQUIRED BY JPA
    // -----------------------------------
    public JobSeeker() {
        super();
    }

    // -----------------------------------
    // EXISTING CONSTRUCTOR
    // -----------------------------------
    public JobSeeker(String name, String email, String password, UserRole role) {
        super(name, email, password, role);
    }

    // -----------------------------------
    // SKILL MANAGEMENT METHODS
    // -----------------------------------
    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void removeSkill(Skill skill) {
        this.skills.remove(skill);
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}