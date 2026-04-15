package com.workforce.pipeline.model;
public class GapAnalysis {

    private int id;
    private String region;
    private String industry;
    private double roleGapScore;
    private double skillGapScore;

    public GapAnalysis(String region, String industry, double roleGapScore, double skillGapScore) {
        this.region = region;
        this.industry = industry;
        this.roleGapScore = roleGapScore;
        this.skillGapScore = skillGapScore;
    }

    // ----------------------------
    // GETTERS / SETTERS
    // ----------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public double getRoleGapScore() {
        return roleGapScore;
    }

    public void setRoleGapScore(double roleGapScore) {
        this.roleGapScore = roleGapScore;
    }

    public double getSkillGapScore() {
        return skillGapScore;
    }

    public void setSkillGapScore(double skillGapScore) {
        this.skillGapScore = skillGapScore;
    }
}
