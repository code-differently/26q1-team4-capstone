package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.service.JobService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Integer id) {
        return jobService.getJobById(id);
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Integer id, @RequestBody Job job) {
        return jobService.updateJob(id, job);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Integer id) {
        jobService.deleteJob(id);
    }

    // ADD SKILL (STANDARDIZED)
    @PostMapping("/{jobId}/skills")
    public Job addSkill(@PathVariable Integer jobId, @RequestBody Skill skill) {
        return jobService.addSkillToJob(jobId, skill.getId());
    }

    // REMOVE SKILL
    @DeleteMapping("/{jobId}/skills/{skillId}")
    public Job removeSkill(@PathVariable Integer jobId, @PathVariable Integer skillId) {
        return jobService.removeSkillFromJob(jobId, skillId);
    }

    // ROLE ASSIGN
    @PostMapping("/{jobId}/role/{roleId}")
    public Job assignRole(@PathVariable Integer jobId, @PathVariable Integer roleId) {
        return jobService.assignRoleToJob(jobId, roleId);
    }
}