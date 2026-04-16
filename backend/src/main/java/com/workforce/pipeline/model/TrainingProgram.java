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
    private int id;

    @Getter
    private String name;

    @Getter
    private String description;

    @ManyToMany
    @JoinTable(
            name = "training_program_skill",
            joinColumns = @JoinColumn(name = "training_program_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skillsTaught = new ArrayList<>();

    public TrainingProgram() {}

    public TrainingProgram(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addSkill(Skill skill) {
        skillsTaught.add(skill);
    }

    public void removeSkill(Skill skill) {
        skillsTaught.remove(skill);
    }

    public List<Skill> getSkillsTaught() {
        return new ArrayList<>(skillsTaught);
    }
}