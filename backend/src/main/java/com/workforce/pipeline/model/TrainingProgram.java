package com.workforce.pipeline.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_program")
public class TrainingProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Getter
    private String name;

    @Getter
    private String description;

    // ----------------------------
    // MANY-TO-MANY: TrainingProgram ↔ Skill
    // ----------------------------
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "training_program_skill",
            joinColumns = @JoinColumn(name = "training_program_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();

    public TrainingProgram() {}

    public TrainingProgram(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ----------------------------
    // ADD SKILL TO PROGRAM
    // ----------------------------
    public void addSkill(Skill skill) {
        if (!skills.contains(skill)) {
            skills.add(skill);
        }
    }

    // ----------------------------
    // REMOVE SKILL FROM PROGRAM
    // ----------------------------
    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    // ----------------------------
    // GET SKILLS (RETURN LIVE LIST FOR JPA)
    // ----------------------------
    public List<Skill> getSkills() {
        return skills;
    }

    // ----------------------------
    // SETTERS
    // ----------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}