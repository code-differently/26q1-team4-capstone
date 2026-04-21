package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Role;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.RoleRepository;
import com.workforce.pipeline.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final RoleRepository roleRepository;

    public JobService(JobRepository jobRepository, SkillRepository skillRepository, RoleRepository roleRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.roleRepository = roleRepository;
    }

    // =========================
    // ADZUNA IMPORT
    // Maps all fields from AdzunaService parsed data to Job entity
    // =========================
    public List<Job> importFromAdzuna(List<Map<String, Object>> adzunaJobs) {
        List<Job> savedJobs = new ArrayList<>();

        for (Map<String, Object> j : adzunaJobs) {
            try {
                String jobKey = String.valueOf(j.get("id"));
                if (jobKey == null || jobKey.equals("null") || jobKey.isEmpty()) continue;

                // Skip duplicates
                if (jobRepository.findByJobKey(jobKey).isPresent()) continue;

                Job job = new Job();
                job.setJobKey(jobKey);
                job.setTitle((String) j.getOrDefault("title", "Untitled"));
                job.setDescription((String) j.getOrDefault("description", ""));
                job.setCompany((String) j.getOrDefault("company", "Unknown Company"));
                job.setLocation((String) j.getOrDefault("location", "Unknown Location"));
                job.setSalaryRange((String) j.getOrDefault("salaryRange", ""));
                job.setFetchedAt((String) j.getOrDefault("fetchedAt", ""));
                job.setRequiredSkills((String) j.getOrDefault("requiredSkills", ""));
                job.setDatePosted(new Date());
                job.setDataFreshness("FRESH");

                savedJobs.add(jobRepository.save(job));

            } catch (Exception e) {
                System.err.println("❌ Failed to import job: " + e.getMessage());
            }
        }

        System.out.println("✅ Imported " + savedJobs.size() + " new jobs from Adzuna");
        return savedJobs;
    }

    // =========================
    // BASIC CRUD
    // =========================

    public Job createJob(Job job) {
        job.setDatePosted(new Date());
        job.setDataFreshness("FRESH");
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Integer id) {
        return jobRepository.findById(id).orElse(null);
    }

    public Job updateJob(Integer id, Job updated) {
        Optional<Job> existing = jobRepository.findById(id);
        if (existing.isEmpty()) return null;
        Job job = existing.get();
        job.setTitle(updated.getTitle());
        job.setDescription(updated.getDescription());
        return jobRepository.save(job);
    }

    public boolean deleteJob(Integer id) {
        if (!jobRepository.existsById(id)) return false;
        jobRepository.deleteById(id);
        return true;
    }

    // =========================
    // SKILLS
    // =========================

    public Job addSkillToJob(Integer jobId, Integer skillId) {
        Job job = getJobById(jobId);
        if (job == null) throw new RuntimeException("Job not found: " + jobId);

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found: " + skillId));

        boolean alreadyHas = job.getSkillsList().stream()
                .anyMatch(s -> s.getId().equals(skillId));
        if (!alreadyHas) {
            job.getSkillsList().add(skill);
        }
        return jobRepository.save(job);
    }

    public Job removeSkillFromJob(Integer jobId, Integer skillId) {
        Job job = getJobById(jobId);
        if (job == null) throw new RuntimeException("Job not found: " + jobId);
        job.getSkillsList().removeIf(s -> s.getId().equals(skillId));
        return jobRepository.save(job);
    }

    public Job assignRoleToJob(Integer jobId, Integer roleId) {
        Job job = getJobById(jobId);
        if (job == null) throw new RuntimeException("Job not found: " + jobId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
        job.setRole(role);
        return jobRepository.save(job);
    }
}
