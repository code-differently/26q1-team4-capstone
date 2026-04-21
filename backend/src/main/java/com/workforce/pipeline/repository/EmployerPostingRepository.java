package com.workforce.pipeline.repository;

import com.workforce.pipeline.model.EmployerPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerPostingRepository extends JpaRepository<EmployerPosting, Integer> {
}
