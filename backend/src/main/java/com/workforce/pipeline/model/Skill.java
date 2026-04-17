package com.workforce.pipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workforce.pipeline.enums.DemandLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skills")
public class Skill {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Column(nullable = false, unique = true)
    private String name;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private DemandLevel demandLevel;

    @Setter
    @Getter
    @JsonIgnore
    @ManyToMany(mappedBy = "skillsList")
    private List<Job> jobs = new ArrayList<>();

    @Setter
    @Getter
    @JsonIgnore
    @ManyToMany(mappedBy = "skills")
    private List<TrainingProgram> trainingPrograms = new ArrayList<>();

    public Skill() {
    }

    // Keep storage normalized so duplicate checks are reliable.
    public void setName(String name) {
        this.name = name.trim().toLowerCase();
    }

    public boolean compareSkills(Skill skill) {
        return this.name.equalsIgnoreCase(skill.getName());
    }
    // Placeholder until demand is computed through the service/repository layer.
    public static DemandLevel calculateDemandLevel() {
        int jobCount = 20000;

        if (jobCount > 5000) {
            return DemandLevel.EXTREMELY_HIGH;
        } else if (jobCount > 2500) {
            return DemandLevel.HIGH;
        } else if (jobCount > 1000) {
            return DemandLevel.MEDIUM;
        } else if (jobCount >= 500) {
            return DemandLevel.LOW;
        } else {
            return DemandLevel.MINIMAL;
        }
    }
}
