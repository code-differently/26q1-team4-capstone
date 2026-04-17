package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.DemandLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkillTest {

    @Test
    void setNameNormalizesWhitespaceAndCase() {
        Skill skill = new Skill();
        skill.setName("  JavaScript  ");
        assertEquals("javascript", skill.getName());
    }

    @Test
    void compareSkillsIgnoresCase() {
        Skill first = new Skill();
        first.setName("java");
        Skill second = new Skill();
        second.setName("JAVA");

        assertTrue(first.compareSkills(second));
    }

    @Test
    void calculateDemandLevelReturnsExpectedStaticValue() {
        assertEquals(DemandLevel.EXTREMELY_HIGH, Skill.calculateDemandLevel());
    }
}
