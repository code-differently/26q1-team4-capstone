package com.workforce.pipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@Entity
@Table(name = "jobs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // company and location from Adzuna company.display_name / location.display_name
    private String company;
    private String location;

    // "$80000-$100000" formatted from salary_min / salary_max
    private String salaryRange;

    // ISO date string from Adzuna "created" field
    private String fetchedAt;

    // comma-separated skills extracted from description
    @Column(columnDefinition = "TEXT")
    private String requiredSkills;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePosted;

    private String dataFreshness;

    @Column(unique = true, length = 1000)
    private String jobKey;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "job_skill",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skillsList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public Job() {}

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
