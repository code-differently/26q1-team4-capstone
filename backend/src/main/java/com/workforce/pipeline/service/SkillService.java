package com.workforce.pipeline.service;

import com.workforce.pipeline.enums.DemandLevel;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillService {
    private final SkillRepository skillRepository;
    private final JobRepository jobRepository;

    public SkillService(SkillRepository skillRepository, JobRepository jobRepository) {
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
    }

    public Skill createSkill(Skill skill) {
        if (skill.getName() == null || skill.getName().isBlank()) {
            throw new IllegalArgumentException("Skill name is required.");
        }

        skill.setName(skill.getName());
        if (skillRepository.existsByNameIgnoreCase(skill.getName())) {
            throw new IllegalArgumentException("Skill already exists.");
        }

        skill.setDemandLevel(calculateDemandLevelForSkill(skill.getName()));
        return skillRepository.save(skill);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(Integer id) {
        return skillRepository.findById(id).orElse(null);
    }

    public Skill getSkillByName(String name) {
        return skillRepository.findByNameIgnoreCase(name).orElse(null);
    }

    public Skill updateSkill(Integer id, Skill updatedSkill) {
        Skill existingSkill = skillRepository.findById(id).orElse(null);
        if (existingSkill == null) {
            return null;
        }

        if (updatedSkill.getName() != null && !updatedSkill.getName().isBlank()) {
            String normalizedName = updatedSkill.getName().trim().toLowerCase();
            Skill duplicateSkill = skillRepository.findByNameIgnoreCase(normalizedName).orElse(null);
            if (duplicateSkill != null && duplicateSkill.getId() != id) {
                throw new IllegalArgumentException("Skill already exists.");
            }

            existingSkill.setName(normalizedName);
            existingSkill.setDemandLevel(calculateDemandLevelForSkill(normalizedName));
        }

        if (updatedSkill.getJobs() != null && !updatedSkill.getJobs().isEmpty()) {
            existingSkill.setJobs(updatedSkill.getJobs());
        }

        if (updatedSkill.getTrainingPrograms() != null && !updatedSkill.getTrainingPrograms().isEmpty()) {
            existingSkill.setTrainingPrograms(updatedSkill.getTrainingPrograms());
        }

        return skillRepository.save(existingSkill);
    }

    public boolean deleteSkill(Integer id) {
        if (!skillRepository.existsById(id)) {
            return false;
        }

        skillRepository.deleteById(id);
        return true;
    }

    public Skill refreshDemandLevel(Integer id) {
        Skill skill = skillRepository.findById(id).orElse(null);
        if (skill == null) {
            return null;
        }

        skill.setDemandLevel(calculateDemandLevelForSkill(skill.getName()));
        return skillRepository.save(skill);
    }

    public DemandLevel calculateDemandLevelForSkill(String skillName) {
        int jobCount = jobRepository.findBySkillsList_NameIgnoreCase(skillName).size();

        if (jobCount > 5000) {
            return DemandLevel.EXTREMELY_HIGH;
        } else if (jobCount > 2500) {
            return DemandLevel.HIGH;
        } else if (jobCount > 1000) {
            return DemandLevel.MEDIUM;
        } else if (jobCount >= 500) {
            return DemandLevel.LOW;
        } else {
            return DemandLevel.MINIMAL;
        }
    }
}
