package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.RecommendationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class RecommendationTest {

    @Test
    void constructorSetsTypeAndExplanation() {
        Recommendation recommendation = new Recommendation(RecommendationType.JOB, "High skill match");

        assertEquals(RecommendationType.JOB, recommendation.getType());
        assertEquals("High skill match", recommendation.getExplanation());
    }

    @Test
    void jobAndTrainingProgramCanBeAssigned() {
        Recommendation recommendation = new Recommendation(RecommendationType.TRAINING, "Need upskilling");
        Job job = new Job();
        TrainingProgram program = new TrainingProgram("Bootcamp", "Learn Java");

        recommendation.setJob(job);
        recommendation.setTrainingProgram(program);

        assertSame(job, recommendation.getJob());
        assertSame(program, recommendation.getTrainingProgram());
    }
}
