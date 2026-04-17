package com.workforce.pipeline.service;

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

    // ✅ CREATE
    public Role createRole(Role role) {
        // 🔥 CRITICAL: ignore client demandScore completely
        role.setDemandScore(0.0);

        return roleRepository.save(role);
    }

    // READ ALL
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // READ ONE
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    // SEARCH
    public List<Role> getRolesByRegion(String region) {
        return roleRepository.findByRegionIgnoreCase(region);
    }

    public List<Role> getRolesByIndustry(String industry) {
        return roleRepository.findByIndustryIgnoreCase(industry);
    }

    // ✅ UPDATE (FIXED PROPERLY)
    public Role updateRole(Integer id, Role updatedRole) {

        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 🔥 DO NOT TOUCH demandScore

        if (updatedRole.getTitle() != null) {
            existing.setTitle(updatedRole.getTitle());
        }

        if (updatedRole.getRegion() != null) {
            existing.setRegion(updatedRole.getRegion());
        }

        if (updatedRole.getIndustry() != null) {
            existing.setIndustry(updatedRole.getIndustry());
        }

        return roleRepository.save(existing);
    }

    // DELETE
    public boolean deleteRole(Integer id) {
        if (!roleRepository.existsById(id)) {
            return false;
        }

        roleRepository.deleteById(id);
        return true;
    }

    // ✅ REFRESH DEMAND SCORE (THIS IS YOUR "AI SIMULATION")
    public Role refreshDemandScore(Integer roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 🔥 COUNT jobs tied to this role
        double score = jobRepository.countByRole_Id(roleId);

        role.setDemandScore(score);

        return roleRepository.save(role);
    }
}