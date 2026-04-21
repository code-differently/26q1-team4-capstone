package com.workforce.pipeline;

import com.workforce.pipeline.model.*;
import com.workforce.pipeline.repository.*;
import com.workforce.pipeline.enums.DemandLevel;
import com.workforce.pipeline.enums.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(
            SkillRepository skillRepository,
            JobRepository jobRepository,
            UserRepository userRepository
    ) {
        return ignored -> {
            if (userRepository.count() > 0) {
                return;
            }


            // =====================
            // SKILLS
            // =====================
            Skill java = new Skill();
            java.setName("Java");
            java.setDemandLevel(DemandLevel.EXTREMELY_HIGH);

            Skill spring = new Skill();
            spring.setName("Spring Boot");
            spring.setDemandLevel(DemandLevel.HIGH);

            Skill sql = new Skill();
            sql.setName("SQL");
            sql.setDemandLevel(DemandLevel.EXTREMELY_HIGH);

            Skill python = new Skill();
            python.setName("Python");
            python.setDemandLevel(DemandLevel.HIGH);

            skillRepository.saveAll(List.of(java, spring, sql, python));

            // =====================
            // JOBS
            // =====================
            Job job1 = new Job();
            job1.setTitle("Software Engineer");
            job1.setDescription("Backend role");
            job1.setSkillsList(List.of(java, spring, sql));

            Job job2 = new Job();
            job2.setTitle("Data Analyst");
            job2.setDescription("Analytics role");
            job2.setSkillsList(List.of(sql));

            Job job3 = new Job();
            job3.setTitle("AI Engineer");
            job3.setDescription("ML role");
            job3.setSkillsList(List.of(python));

            jobRepository.saveAll(List.of(job1, job2, job3));

            // =====================
            // USER
            // =====================
            User user = new User();
            user.setEmail("test@test.com");
            user.setName("Test User");
            user.setPassword("123");
            user.setRole(UserRole.JOB_SEEKER);

            // IMPORTANT: only if your entity supports it
            user.addSkill(java);
            user.addSkill(sql);

            userRepository.save(user);

            System.out.println("✅ DATABASE SEEDED SUCCESSFULLY");
        };
    }
}