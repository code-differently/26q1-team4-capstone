package com.workforce.pipeline.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employer_postings")
public class EmployerPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private String roleTitle;

    @Column(columnDefinition = "TEXT")
    private String skillsNeeded;

    private String companyName;
    private String location;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public EmployerPosting() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getRoleTitle() { return roleTitle; }
    public void setRoleTitle(String roleTitle) { this.roleTitle = roleTitle; }

    public String getSkillsNeeded() { return skillsNeeded; }
    public void setSkillsNeeded(String skillsNeeded) { this.skillsNeeded = skillsNeeded; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
