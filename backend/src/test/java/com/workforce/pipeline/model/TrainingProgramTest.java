package com.workforce.pipeline.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingProgramTest {

    @Test
    void addSkillAddsSkillToProgram() {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend training");
        Skill skill = new Skill();
        skill.setName("Spring");

        program.addSkill(skill);

        assertEquals(1, program.getSkills().size());
        assertEquals("spring", program.getSkills().getFirst().getName());
    }

    @Test
    void removeSkillRemovesSkillFromProgram() {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend training");
        Skill skill = new Skill();
        skill.setName("Spring");
        program.addSkill(skill);

        program.removeSkill(skill);

        assertTrue(program.getSkills().isEmpty());
    }

    @Test
    void addSkillDoesNotCreateDuplicateEntries() {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend training");
        Skill skill = new Skill();
        skill.setName("Spring");
        program.addSkill(skill);
        program.addSkill(skill);

        assertEquals(1, program.getSkills().size());
    }
}
