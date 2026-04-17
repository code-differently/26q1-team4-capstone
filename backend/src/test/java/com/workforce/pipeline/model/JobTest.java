package com.workforce.pipeline.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JobTest {

    @Test
    void addSkillAddsSkillToJob() {
        Job job = new Job();
        Skill skill = new Skill();
        skill.setName("Java");

        job.addSkill(skill);

        assertEquals(1, job.getSkillsList().size());
        assertEquals("java", job.getSkillsList().getFirst().getName());
    }

    @Test
    void removeSkillRemovesSkillFromJob() {
        Job job = new Job();
        Skill skill = new Skill();
        skill.setName("Java");
        job.addSkill(skill);

        job.removeSkill(skill);

        assertTrue(job.getSkillsList().isEmpty());
    }

    @Test
    void constructorPopulatesCoreFields() {
        Date posted = new Date();
        Job job = new Job(5, posted, "Build APIs", "Backend Developer");

        assertEquals(5, job.getId());
        assertEquals(posted, job.getDatePosted());
        assertEquals("Build APIs", job.getDescription());
        assertEquals("Backend Developer", job.getTitle());
    }
}
