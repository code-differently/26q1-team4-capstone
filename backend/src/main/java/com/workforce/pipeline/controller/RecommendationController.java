package com.workforce.pipeline.controller;

import com.workforce.pipeline.service.RecommendationService;
import com.workforce.pipeline.repository.UserRepository;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.model.Job;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin
public class RecommendationController {

    private final RecommendationService aiService;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public RecommendationController(RecommendationService aiService,
                                    UserRepository userRepository,
                                    JobRepository jobRepository) {
        this.aiService = aiService;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    @PostMapping("/generate")
    public Map<String, Object> generate(@RequestBody Map<String, Object> request) {

        // =========================
        // SAFE REQUEST PARSING
        // =========================
        Integer userId;
        try {
            userId = request.get("userId") == null
                    ? null
                    : Integer.parseInt(request.get("userId").toString());
        } catch (Exception e) {
            throw new RuntimeException("Invalid userId format");
        }

        List<?> rawJobIds = (List<?>) request.get("jobIds");

        if (userId == null) {
            throw new RuntimeException("userId cannot be null");
        }

        if (rawJobIds == null || rawJobIds.isEmpty()) {
            throw new RuntimeException("jobIds cannot be null or empty");
        }

        List<Integer> jobIds = rawJobIds.stream()
                .map(id -> Integer.parseInt(id.toString()))
                .toList();

        // =========================
        // DATABASE FETCH
        // =========================
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        List<Job> jobs = jobRepository.findAllWithSkills(jobIds);

        if (jobs.isEmpty()) {
            return Map.of(
                    "error", "No valid jobs found",
                    "jobIds", jobIds
            );
        }

        // =========================
        // SKILL EXTRACTION
        // =========================
        List<String> userSkills = user.getSkills()
                .stream()
                .map(skill -> skill.getName())
                .collect(Collectors.toList());

        List<String> jobSkills = jobs.stream()
                .flatMap(job -> job.getSkillsList().stream())
                .map(skill -> skill.getName())
                .distinct()
                .collect(Collectors.toList());

        String primaryJobTitle = jobs.get(0).getTitle();

        // =========================
        // AI CALL
        // =========================
        Map<String, Object> response = aiService.generateRecommendations(
                userSkills,
                jobSkills,
                primaryJobTitle
        );

        // =========================
        // SAFE RESPONSE WRAP
        // =========================
        response.put("userId", userId);
        response.put("jobCount", jobs.size());

        return response;
    }
}