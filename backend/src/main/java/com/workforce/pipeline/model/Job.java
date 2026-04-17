package com.workforce.pipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "jobs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePosted;

    private String dataFreshness;

    @ManyToMany
    @JoinTable(
            name = "job_skill",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skillsList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Job() {
    }

    public Job(int id, Date datePosted, String description, String title) {
        this.id = id;
        this.datePosted = datePosted;
        this.description = description;
        this.title = title;
    }

    public Job(int id, List<Skill> skillsList, String dataFreshness, Date datePosted, String description, String title) {
        this.id = id;
        this.skillsList = skillsList;
        this.dataFreshness = dataFreshness;
        this.datePosted = datePosted;
        this.description = description;
        this.title = title;
    }

    public void addSkill(Skill skill) {
        skillsList.add(skill);
    }

    public void removeSkill(Skill skill) {
        skillsList.remove(skill);
    }
}
