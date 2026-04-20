package com.workforce.pipeline.controller;

import com.workforce.pipeline.service.RecommendationService;
import com.workforce.pipeline.repository.UserRepository;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.model.Job;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

        Integer userId = (Integer) request.get("userId");
        Integer jobId = (Integer) request.get("jobId");

        User user = userRepository.findById(userId).orElseThrow();
        Job job = jobRepository.findById(jobId).orElseThrow();

        List<String> userSkills = user.getSkills()
                .stream().map(s -> s.getName()).toList();

        List<String> jobSkills = job.getSkillsList()
                .stream().map(s -> s.getName()).toList();

        return aiService.generateRecommendations(
                userSkills,
                jobSkills,
                job.getTitle()
        );
    }
}