package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Role;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private final JobRepository jobRepository;

    public RoleService(RoleRepository roleRepository, JobRepository jobRepository) {
        this.roleRepository = roleRepository;
        this.jobRepository = jobRepository;
    }

    public Role createRole(Role role) {
        if (role.getRegion() == null || role.getRegion().isBlank()) {
            throw new IllegalArgumentException("Role region is required.");
        }
        if (role.getIndustry() == null || role.getIndustry().isBlank()) {
            throw new IllegalArgumentException("Role industry is required.");
        }

        roleRepository.findByRegionIgnoreCaseAndIndustryIgnoreCase(role.getRegion(), role.getIndustry())
                .ifPresent(existingRole -> {
                    throw new IllegalArgumentException("Role already exists for this region and industry.");
                });

        role.setDemandScore(calculateDemandScore(role));
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    public List<Role> getRolesByRegion(String region) {
        return roleRepository.findByRegionIgnoreCase(region);
    }

    public List<Role> getRolesByIndustry(String industry) {
        return roleRepository.findByIndustryIgnoreCase(industry);
    }

    public Role updateRole(Integer id, Role updatedRole) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole == null) {
            return null;
        }

        if (updatedRole.getRegion() != null && !updatedRole.getRegion().isBlank()) {
            existingRole.setRegion(updatedRole.getRegion());
        }
        if (updatedRole.getIndustry() != null && !updatedRole.getIndustry().isBlank()) {
            existingRole.setIndustry(updatedRole.getIndustry());
        }

        existingRole.setDemandScore(calculateDemandScore(existingRole));
        return roleRepository.save(existingRole);
    }

    public boolean deleteRole(Integer id) {
        if (!roleRepository.existsById(id)) {
            return false;
        }

        roleRepository.deleteById(id);
        return true;
    }

    public Role refreshDemandScore(Integer roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null) {
            return null;
        }

        role.setDemandScore(calculateDemandScore(role));
        return roleRepository.save(role);
    }

    public double calculateDemandScore(Role role) {
        if (role.getId() == 0) {
            return 0.0;
        }

        List<Job> matchingJobs = jobRepository.findByRole_Id(role.getId());
        return matchingJobs.size();
    }
}
