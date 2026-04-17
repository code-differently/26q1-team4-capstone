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
        role.setDemandScore(0.0);
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

        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (updatedRole.getRegion() != null) {
            existing.setRegion(updatedRole.getRegion());
        }

        if (updatedRole.getIndustry() != null) {
            existing.setIndustry(updatedRole.getIndustry());
        }

        return roleRepository.save(existing);
    }

    public boolean deleteRole(Integer id) {
        if (!roleRepository.existsById(id)) {
            return false;
        }
        roleRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Role refreshDemandScore(Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        double score = jobRepository.countByRole_Id(roleId);

        role.setDemandScore(score);

        return roleRepository.save(role);
    }

    public double calculateDemandScore(Role role) {
        if (role.getId() == 0) {
            return 0.0;
        }

        return jobRepository.findByRole_Id(role.getId()).size();
    }
}