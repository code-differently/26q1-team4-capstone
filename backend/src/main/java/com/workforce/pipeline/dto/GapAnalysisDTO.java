package com.workforce.pipeline.dto;

/**
 * GapAnalysisDTO
 *
 * Represents workforce gap metrics for a given region and industry.
 * Used for analytics responses (NOT stored in database).
 */
public class GapAnalysisDTO {

    // Geographic region (e.g., "Delaware", "NYC", etc.)
    private String region;

    // Industry sector (e.g., "Healthcare", "Tech")
    private String industry;

    // Measures mismatch between job demand and available roles
    private double roleGapScore;

    // Measures mismatch between required skills and available skills
    private double skillGapScore;

    public GapAnalysisDTO() {}

    public GapAnalysisDTO(String region, String industry,
                          double roleGapScore, double skillGapScore) {
        this.region = region;
        this.industry = industry;
        this.roleGapScore = roleGapScore;
        this.skillGapScore = skillGapScore;
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public double getRoleGapScore() { return roleGapScore; }
    public void setRoleGapScore(double roleGapScore) { this.roleGapScore = roleGapScore; }

    public double getSkillGapScore() { return skillGapScore; }
    public void setSkillGapScore(double skillGapScore) { this.skillGapScore = skillGapScore; }
}