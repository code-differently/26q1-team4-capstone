package com.workforce.pipeline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "skill_profiles")
public class SkillProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Column(columnDefinition = "TEXT")
    private String skills;

    private String targetRole;
    private String experienceLevel;

    @JsonProperty("isDiscoverable")
    private Boolean isDiscoverable = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public SkillProfile() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    @JsonProperty("isDiscoverable")
    public Boolean getIsDiscoverable() { return isDiscoverable; }

    @JsonProperty("isDiscoverable")
    public void setIsDiscoverable(Boolean isDiscoverable) { this.isDiscoverable = isDiscoverable; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
