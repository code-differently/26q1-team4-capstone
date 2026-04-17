package com.workforce.pipeline.service;

import com.workforce.pipeline.model.TrainingProgram;
import com.workforce.pipeline.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    // CREATE
    public TrainingProgram createTrainingProgram(TrainingProgram program) {
        return trainingRepository.save(program);
    }

    // GET ALL
    public List<TrainingProgram> getAllPrograms() {
        return trainingRepository.findAll();
    }

    // GET BY ID
    public TrainingProgram getById(int id) {
        return trainingRepository.findById(id).orElse(null);
    }
}