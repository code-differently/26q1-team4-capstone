package com.workforce.pipeline.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "training_program")
public class TrainingProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ----------------------------
    // SETTERS
    // ----------------------------
    @Setter
    private String name;

    @Setter
    private String description;

    // ----------------------------
    // GET SKILLS (RETURN LIVE LIST FOR JPA)
    // ----------------------------
    // ----------------------------
    // MANY-TO-MANY: TrainingProgram ↔ Skill
    // ----------------------------
    @ManyToMany(fetch = FetchType.LAZY)
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

}