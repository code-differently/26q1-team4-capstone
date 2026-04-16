package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Job;
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

    @GetMapping("/search/title")
    public List<Job> searchJobsByTitle(@RequestParam String title) {
        return jobService.searchJobsByTitle(title);
    }

    @GetMapping("/search/skill")
    public List<Job> getJobsBySkill(@RequestParam String skillName) {
        return jobService.getJobsBySkill(skillName);
    }

    @GetMapping("/search/role/{roleId}")
    public List<Job> getJobsByRole(@PathVariable Integer roleId) {
        return jobService.getJobsByRole(roleId);
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Integer id, @RequestBody Job job) {
        return jobService.updateJob(id, job);
    }

    @PutMapping("/{jobId}/skills/{skillId}")
    public Job addSkillToJob(@PathVariable Integer jobId, @PathVariable Integer skillId) {
        return jobService.addSkillToJob(jobId, skillId);
    }

    @DeleteMapping("/{jobId}/skills/{skillId}")
    public Job removeSkillFromJob(@PathVariable Integer jobId, @PathVariable Integer skillId) {
        return jobService.removeSkillFromJob(jobId, skillId);
    }

    @PutMapping("/{jobId}/role/{roleId}")
    public Job assignRoleToJob(@PathVariable Integer jobId, @PathVariable Integer roleId) {
        return jobService.assignRoleToJob(jobId, roleId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteJob(@PathVariable Integer id) {
        return jobService.deleteJob(id);
    }
}
