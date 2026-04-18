package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.TrainingProgram;
import com.workforce.pipeline.repository.SkillRepository;
import com.workforce.pipeline.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final SkillRepository skillRepository;

    public TrainingService(TrainingRepository trainingRepository,
                           SkillRepository skillRepository) {
        this.trainingRepository = trainingRepository;
        this.skillRepository = skillRepository;
    }

    // ----------------------------------------
    // CREATE TRAINING PROGRAM
    // ----------------------------------------
    public TrainingProgram createTrainingProgram(TrainingProgram program) {
        return trainingRepository.save(program);
    }

    // ----------------------------------------
    // GET ALL PROGRAMS
    // ----------------------------------------
    public List<TrainingProgram> getAllPrograms() {
        return trainingRepository.findAll();
    }

    // ----------------------------------------
    // GET PROGRAM BY ID
    // ----------------------------------------
    public TrainingProgram getById(int id) {
        return trainingRepository.findById(id).orElse(null);
    }

    // ----------------------------------------
    // UPDATE PROGRAM
    // ----------------------------------------
    public TrainingProgram updateProgram(int id, TrainingProgram updated) {

        TrainingProgram existing = trainingRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());

        return trainingRepository.save(existing);
    }

    // ----------------------------------------
    // DELETE PROGRAM
    // ----------------------------------------
    public void deleteProgram(int id) {
        trainingRepository.deleteById(id);
    }

    // ----------------------------------------
    // ADD SKILL TO PROGRAM
    // FIX: prevent duplicates + safe null handling
    // ----------------------------------------
    public TrainingProgram addSkillToProgram(int programId, int skillId) {

        TrainingProgram program = trainingRepository.findById(programId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);

        if (program == null || skill == null) return null;

        // prevent duplicate skill inserts (important for Postgres join table)
        boolean alreadyExists = program.getSkills()
                .stream()
                .anyMatch(s -> s.getId().equals(skillId));

        if (!alreadyExists) {
            program.getSkills().add(skill);
        }

        return trainingRepository.save(program);
    }

    // ----------------------------------------
    // REMOVE SKILL FROM PROGRAM
    // FIX: use equals() not == (VERY IMPORTANT for Postgres IDs)
    // ----------------------------------------
    public TrainingProgram removeSkillFromProgram(int programId, int skillId) {

        TrainingProgram program = trainingRepository.findById(programId).orElse(null);
        if (program == null) return null;

        program.getSkills().removeIf(skill -> skill.getId().equals(skillId));

        return trainingRepository.save(program);
    }
}