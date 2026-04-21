package com.workforce.pipeline.repository;

import com.workforce.pipeline.model.TrainingOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingOfferingRepository extends JpaRepository<TrainingOffering, Integer> {
}
