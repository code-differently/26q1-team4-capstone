package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Role;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.RoleRepository;
import com.workforce.pipeline.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final RoleRepository roleRepository;

    public JobService(JobRepository jobRepository, SkillRepository skillRepository, RoleRepository roleRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.roleRepository = roleRepository;
    }

    public Job createJob(Job job) {
        if (job.getDatePosted() == null) {
            job.setDatePosted(new Date());
        }
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Integer id) {
        return jobRepository.findById(id).orElse(null);
    }

    public List<Job> searchJobsByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Job> getJobsBySkill(String skillName) {
        return jobRepository.findBySkillsList_NameIgnoreCase(skillName);
    }

    public List<Job> getJobsByRole(Integer roleId) {
        return jobRepository.findByRole_Id(roleId);
    }

    public Job updateJob(Integer id, Job updatedJob) {
        Job existingJob = jobRepository.findById(id).orElse(null);
        if (existingJob == null) {
            return null;
        }

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setDatePosted(updatedJob.getDatePosted());
        existingJob.setDataFreshness(updatedJob.getDataFreshness());

        if (updatedJob.getSkillsList() != null) {
            existingJob.setSkillsList(updatedJob.getSkillsList());
        }

        if (updatedJob.getRole() != null) {
            existingJob.setRole(updatedJob.getRole());
        }

        return jobRepository.save(existingJob);
    }

    public boolean deleteJob(Integer id) {
        if (!jobRepository.existsById(id)) {
            return false;
        }

        jobRepository.deleteById(id);
        return true;
    }

    public Job addSkillToJob(Integer jobId, Integer skillId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);
        if (job == null || skill == null) {
            return null;
        }

        boolean alreadyLinked = job.getSkillsList().stream()
                .anyMatch(existingSkill -> existingSkill.getId() == skillId);
        if (!alreadyLinked) {
            job.addSkill(skill);
        }

        return jobRepository.save(job);
    }

    public Job removeSkillFromJob(Integer jobId, Integer skillId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            return null;
        }

        job.getSkillsList().removeIf(skill -> skill.getId() == skillId);
        return jobRepository.save(job);
    }

    public Job assignRoleToJob(Integer jobId, Integer roleId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        if (job == null || role == null) {
            return null;
        }

        job.setRole(role);
        return jobRepository.save(job);
    }
}
