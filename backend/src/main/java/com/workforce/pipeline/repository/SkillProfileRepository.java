package com.workforce.pipeline.repository;

import com.workforce.pipeline.model.SkillProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillProfileRepository extends JpaRepository<SkillProfile, Integer> {
    Optional<SkillProfile> findByUserId(Integer userId);
    List<SkillProfile> findByIsDiscoverableTrue();
    List<SkillProfile> findBySkillsContainingIgnoreCaseAndIsDiscoverableTrue(String keyword);
}
