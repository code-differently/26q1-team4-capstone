package com.workforce.pipeline.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "training_offerings")
public class TrainingOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private String programName;

    @Column(columnDefinition = "TEXT")
    private String skillsCovered;

    private String format;
    private String contactInfo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public TrainingOffering() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getSkillsCovered() { return skillsCovered; }
    public void setSkillsCovered(String skillsCovered) { this.skillsCovered = skillsCovered; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
