package com.workforce.pipeline.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GapAnalysisTest {

    @Test
    void constructorPopulatesAllFields() {
        GapAnalysis gapAnalysis = new GapAnalysis("MA", "Tech", 4.5, 2.0);

        assertEquals("MA", gapAnalysis.getRegion());
        assertEquals("Tech", gapAnalysis.getIndustry());
        assertEquals(4.5, gapAnalysis.getRoleGapScore());
        assertEquals(2.0, gapAnalysis.getSkillGapScore());
    }

    @Test
    void settersUpdateScoresAndMetadata() {
        GapAnalysis gapAnalysis = new GapAnalysis();

        gapAnalysis.setRegion("RI");
        gapAnalysis.setIndustry("Healthcare");
        gapAnalysis.setRoleGapScore(1.2);
        gapAnalysis.setSkillGapScore(0.7);

        assertEquals("RI", gapAnalysis.getRegion());
        assertEquals("Healthcare", gapAnalysis.getIndustry());
        assertEquals(1.2, gapAnalysis.getRoleGapScore());
        assertEquals(0.7, gapAnalysis.getSkillGapScore());
    }
}
