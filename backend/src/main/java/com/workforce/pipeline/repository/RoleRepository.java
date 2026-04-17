package com.workforce.pipeline.repository;

import com.workforce.pipeline.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByRegionIgnoreCase(String region);

    List<Role> findByIndustryIgnoreCase(String industry);

    Optional<Role> findByRegionIgnoreCaseAndIndustryIgnoreCase(String region, String industry);
}
