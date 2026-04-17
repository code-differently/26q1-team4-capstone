package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.TrainingProgram;
import com.workforce.pipeline.service.TrainingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    // ----------------------------
    // CREATE PROGRAM
    // ----------------------------
    @PostMapping
    public TrainingProgram create(@RequestBody TrainingProgram program) {
        return trainingService.createTrainingProgram(program);
    }

    // ----------------------------
    // GET ALL PROGRAMS
    // ----------------------------
    @GetMapping
    public List<TrainingProgram> getAll() {
        return trainingService.getAllPrograms();
    }

    // ----------------------------
    // GET PROGRAM BY ID
    // ----------------------------
    @GetMapping("/{id}")
    public TrainingProgram getById(@PathVariable int id) {
        return trainingService.getById(id);
    }

    // ----------------------------
    // UPDATE PROGRAM
    // ----------------------------
    @PutMapping("/{id}")
    public TrainingProgram update(@PathVariable int id,
                                  @RequestBody TrainingProgram program) {
        return trainingService.updateProgram(id, program);
    }

    // ----------------------------
    // DELETE PROGRAM (returns deleted object for testing visibility)
    // ----------------------------
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        trainingService.deleteProgram(id);
    }

    // ----------------------------
    // ADD SKILL TO PROGRAM (STANDARDIZED PATTERN)
    // ----------------------------
    @PostMapping("/{programId}/skills")
    public TrainingProgram addSkill(@PathVariable int programId,
                                    @RequestBody Skill skill) {
        return trainingService.addSkillToProgram(programId, skill.getId());
    }

    // ----------------------------
    // REMOVE SKILL FROM PROGRAM
    // ----------------------------
    @DeleteMapping("/{programId}/skills/{skillId}")
    public TrainingProgram removeSkill(@PathVariable int programId,
                                       @PathVariable int skillId) {
        return trainingService.removeSkillFromProgram(programId, skillId);
    }
}