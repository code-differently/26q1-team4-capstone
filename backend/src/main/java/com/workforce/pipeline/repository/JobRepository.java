package com.workforce.pipeline.repository;

import com.workforce.pipeline.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findBySkillsList_NameIgnoreCase(String skillName);

    List<Job> findByRole_Id(Integer roleId);

    long countByRole_Id(Integer roleId);
}
