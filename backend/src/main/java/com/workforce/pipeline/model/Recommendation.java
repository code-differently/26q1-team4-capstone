package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.RecommendationType;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // API contract fields
    private Integer userId;

    @Column(columnDefinition = "TEXT")
    private String searchQuery;

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Legacy POJO fields — kept for existing tests
    @Enumerated(EnumType.STRING)
    private RecommendationType type;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id")
    private TrainingProgram trainingProgram;

    public Recommendation() {}

    // Keep this constructor so existing RecommendationTest passes
    public Recommendation(RecommendationType type, String explanation) {
        this.type = type;
        this.explanation = explanation;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

    public String getAiResponse() { return aiResponse; }
    public void setAiResponse(String aiResponse) { this.aiResponse = aiResponse; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public RecommendationType getType() { return type; }
    public void setType(RecommendationType type) { this.type = type; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public TrainingProgram getTrainingProgram() { return trainingProgram; }
    public void setTrainingProgram(TrainingProgram trainingProgram) { this.trainingProgram = trainingProgram; }
}
