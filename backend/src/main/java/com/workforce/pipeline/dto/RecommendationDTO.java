package com.workforce.pipeline.dto;

import java.util.List;

/**
 * RecommendationDTO
 *
 * Purpose:
 * - Represents job recommendations for a specific user
 * - Will be used later for analytics / AI logic
 */
public class RecommendationDTO {

    private Integer userId;
    private List<JobDTO> recommendedJobs;

    public RecommendationDTO() {}

    public RecommendationDTO(Integer userId, List<JobDTO> recommendedJobs) {
        this.userId = userId;
        this.recommendedJobs = recommendedJobs;
    }

    // ===== Getters & Setters =====

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<JobDTO> getRecommendedJobs() {
        return recommendedJobs;
    }

    public void setRecommendedJobs(List<JobDTO> recommendedJobs) {
        this.recommendedJobs = recommendedJobs;
    }
}