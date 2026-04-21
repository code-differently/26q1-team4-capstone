package com.workforce.pipeline.repository;

import com.workforce.pipeline.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {
    List<Recommendation> findByUserIdOrderByCreatedAtDesc(Integer userId);
}
