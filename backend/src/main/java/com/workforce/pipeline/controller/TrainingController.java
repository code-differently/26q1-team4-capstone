package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.TrainingProgram;
import com.workforce.pipeline.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    // CREATE PROGRAM
    @PostMapping
    public TrainingProgram create(@RequestBody TrainingProgram program) {
        return trainingService.createTrainingProgram(program);
    }

    // GET ALL
    @GetMapping
    public List<TrainingProgram> getAll() {
        return trainingService.getAllPrograms();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public TrainingProgram getById(@PathVariable int id) {
        return trainingService.getById(id);
    }

    @PostMapping("/{id}/skills")
    public TrainingProgram addSkill(@PathVariable int id, @RequestBody Skill skill) {
        TrainingProgram program = trainingService.getById(id);
        program.addSkill(skill);
        return trainingService.createTrainingProgram(program);
    }
}