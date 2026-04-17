package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill saveSkill(Skill skill) {

        // normalize name (already done in model, but extra safe)
        String name = skill.getName();

        // check if skill already exists
        if (skillRepository.existsByNameIgnoreCase(name)) {
            return skillRepository.findByNameIgnoreCase(name).get();
        }

        return skillRepository.save(skill);
    }

    // READ ALL
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    // READ BY ID
    public Skill getSkillById(int id) {
        return skillRepository.findById(id).orElse(null);
    }

    // UPDATE
    public Skill updateSkill(int id, Skill updatedSkill) {
        Skill existing = skillRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(updatedSkill.getName());
            existing.setDemandLevel(updatedSkill.getDemandLevel());
            return skillRepository.save(existing);
        }

        return null;
    }

    // DELETE
    public void deleteSkill(int id) {
        skillRepository.deleteById(id);
    }
}