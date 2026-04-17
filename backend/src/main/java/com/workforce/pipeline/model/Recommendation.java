package com.workforce.pipeline.model;
import com.workforce.pipeline.enums.RecommendationType;

public class Recommendation {

    private RecommendationType type; // JOB or TRAINING
    private String explanation;

    private Job job;
    private TrainingProgram trainingProgram;

    public Recommendation(RecommendationType type, String explanation) {
        this.type = type;
        this.explanation = explanation;
    }

    // ----------------------------
    // JOB REFERENCE
    // ----------------------------

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    // ----------------------------
    // TRAINING PROGRAM REFERENCE
    // ----------------------------

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    // ----------------------------
    // TYPE (ENUM)
    // ----------------------------

    public RecommendationType getType() {
        return type;
    }

    public void setType(RecommendationType type) {
        this.type = type;
    }

    // ----------------------------
    // EXPLANATION (AI REASONING TEXT)
    // ----------------------------

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}