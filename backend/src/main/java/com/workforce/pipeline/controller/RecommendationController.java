package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Recommendation;
import com.workforce.pipeline.model.SkillProfile;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.RecommendationRepository;
import com.workforce.pipeline.repository.SkillProfileRepository;
import com.workforce.pipeline.repository.UserRepository;
import com.workforce.pipeline.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final SkillProfileRepository skillProfileRepository;

    public RecommendationController(
            RecommendationService recommendationService,
            RecommendationRepository recommendationRepository,
            UserRepository userRepository,
            JobRepository jobRepository,
            SkillProfileRepository skillProfileRepository
    ) {
        this.recommendationService = recommendationService;
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.skillProfileRepository = skillProfileRepository;
    }

    // POST /api/recommendations
    // Body: { userId, skillProfileId, jobPostingIds[] }
    @PostMapping
    public ResponseEntity<?> generate(@RequestBody Map<String, Object> body) {
        Integer userId = parseId(body.get("userId"));
        Integer skillProfileId = parseId(body.get("skillProfileId"));

        List<?> rawIds = (List<?>) body.get("jobPostingIds");

        if (userId == null) return ResponseEntity.badRequest().body(Map.of("error", "userId is required"));
        if (rawIds == null || rawIds.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error", "jobPostingIds is required"));

        List<Integer> jobIds = rawIds.stream().map(id -> Integer.parseInt(id.toString())).toList();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.badRequest().body(Map.of("error", "User not found: " + userId));

        List<Job> jobs = jobRepository.findAllWithSkills(jobIds);
        if (jobs.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error", "No valid jobs found for provided IDs"));

        SkillProfile profile = null;
        if (skillProfileId != null) {
            profile = skillProfileRepository.findById(skillProfileId).orElse(null);
        }
        if (profile == null) {
            profile = skillProfileRepository.findByUserId(userId).orElse(null);
        }

        // Build search query from job titles
        String searchQuery = jobs.stream()
                .map(j -> j.getTitle() != null ? j.getTitle() : "")
                .filter(t -> !t.isEmpty())
                .distinct()
                .limit(3)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Job Search");

        String aiResponse = recommendationService.generateRecommendation(user, profile, jobs);

        Recommendation rec = new Recommendation();
        rec.setUserId(userId);
        rec.setSearchQuery(searchQuery);
        rec.setAiResponse(aiResponse);
        rec.setCreatedAt(new Date());

        Recommendation saved = recommendationRepository.save(rec);
        return ResponseEntity.ok(saved);
    }

    // GET /api/recommendations/history/{userId}
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Recommendation>> history(@PathVariable Integer userId) {
        return ResponseEntity.ok(recommendationRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    private Integer parseId(Object val) {
        if (val == null) return null;
        try {
            return Integer.parseInt(val.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
