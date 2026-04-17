package com.workforce.pipeline.repository;

import com.workforce.pipeline.model.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingProgram, Integer> {
    List<TrainingProgram> findByNameContainingIgnoreCase(String name);

    List<TrainingProgram> findBySkills_NameIgnoreCase(String name);}
