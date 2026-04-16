package com.workforce.pipeline.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TrainingProgram {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<Skill> skillsTaught;

    public TrainingProgram() {
        this.skillsTaught = new ArrayList<>();
    }

    public TrainingProgram(String name, String description) {
        this.name = name;
        this.description = description;
        this.skillsTaught = new ArrayList<>();
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
