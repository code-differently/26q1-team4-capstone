package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.TrainingProgram;
import com.workforce.pipeline.repository.SkillRepository;
import com.workforce.pipeline.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final SkillRepository skillRepository;

    public TrainingService(TrainingRepository trainingRepository, SkillRepository skillRepository) {
        this.trainingRepository = trainingRepository;
        this.skillRepository = skillRepository;
    }

    // CREATE
    public TrainingProgram createTrainingProgram(TrainingProgram program) {
        return trainingRepository.save(program);
    }

    // READ ALL
    public List<TrainingProgram> getAllPrograms() {
        return trainingRepository.findAll();
    }

    // READ ONE
    public TrainingProgram getById(int id) {
        return trainingRepository.findById(id).orElse(null);
    }

    // UPDATE (FIX ERROR)
    public TrainingProgram updateProgram(int id, TrainingProgram updated) {
        TrainingProgram existing = trainingRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());

        return trainingRepository.save(existing);
    }

    // DELETE (FIX ERROR)
    public void deleteProgram(int id) {
        trainingRepository.deleteById(id);
    }

    // ADD SKILL
    public TrainingProgram addSkillToProgram(int programId, int skillId) {
        TrainingProgram program = trainingRepository.findById(programId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);

        if (program == null || skill == null) return null;

        program.getSkillsTaught().add(skill);
        return trainingRepository.save(program);
    }

    // REMOVE SKILL (FIX ERROR)
    public TrainingProgram removeSkillFromProgram(int programId, int skillId) {
        TrainingProgram program = trainingRepository.findById(programId).orElse(null);
        if (program == null) return null;

        program.getSkillsTaught().removeIf(skill -> skill.getId() == skillId);
        return trainingRepository.save(program);
    }
}