package com.workforce.pipeline.model;

import com.workforce.pipeline.enums.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void addSkillAddsSkillToUser() {
        User user = new User("Alice", "alice@example.com", "secret", UserRole.JOB_SEEKER);
        Skill skill = new Skill();
        skill.setName("Java");

        user.addSkill(skill);

        assertEquals(1, user.getSkills().size());
        assertEquals("java", user.getSkills().getFirst().getName());
    }

    @Test
    void removeSkillRemovesSkillFromUser() {
        User user = new User("Alice", "alice@example.com", "secret", UserRole.JOB_SEEKER);
        Skill skill = new Skill();
        skill.setName("Java");
        user.addSkill(skill);

        user.removeSkill(skill);

        assertTrue(user.getSkills().isEmpty());
    }

    @Test
    void settersUpdateBasicFields() {
        User user = new User();

        user.setId(7);
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setPassword("pw");
        user.setRole(UserRole.EMPLOYER);

        assertEquals(7, user.getId());
        assertEquals("Bob", user.getName());
        assertEquals("bob@example.com", user.getEmail());
        assertEquals("pw", user.getPassword());
        assertEquals(UserRole.EMPLOYER, user.getRole());
    }
}
