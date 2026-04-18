package com.workforce.pipeline.service;

import com.workforce.pipeline.dto.GapAnalysisDTO;
import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GapAnalysisService
 *
 * Simple explainable workforce analytics engine:
 * - compares job demand vs talent supply
 * - measures skill mismatch severity
 *
 * No AI/ML — fully deterministic and explainable.
 */
@Service
public class GapAnalysisService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public GapAnalysisService(JobRepository jobRepository,
                              UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    /**
     * MAIN GAP ANALYSIS METHOD
     */
    public List<GapAnalysisDTO> computeGapAnalysis() {

        List<Job> jobs = jobRepository.findAll();
        List<User> users = userRepository.findAll();

        Map<String, List<Job>> jobsByRole = jobs.stream()
                .filter(j -> j.getRole() != null)
                .collect(Collectors.groupingBy(j -> j.getRole().getTitle()));

        List<GapAnalysisDTO> results = new ArrayList<>();

        for (Map.Entry<String, List<Job>> entry : jobsByRole.entrySet()) {

            String roleTitle = entry.getKey();
            List<Job> roleJobs = entry.getValue();

            double roleGapScore = computeRoleGap(roleJobs.size(), users.size());
            double skillGapScore = computeSkillGap(roleJobs, users);

            results.add(new GapAnalysisDTO(
                    "ALL", // no region in current model
                    roleTitle,
                    roleGapScore,
                    skillGapScore
            ));
        }

        return results;
    }

    /**
     * ROLE GAP = demand vs supply ratio
     * Higher = more shortage
     */
    private double computeRoleGap(int jobCount, int userCount) {
        if (jobCount == 0) return 0.0;
        if (userCount == 0) return 1.0;

        return (double) jobCount / (jobCount + userCount);
    }

    /**
     * SKILL GAP = % of required skills not covered by users
     */
    private double computeSkillGap(List<Job> jobs, List<User> users) {

        Set<String> userSkills = users.stream()
                .filter(u -> u.getSkills() != null)
                .flatMap(u -> u.getSkills().stream())
                .map(skill -> skill.getName().toLowerCase())
                .collect(Collectors.toSet());

        Set<String> requiredSkills = jobs.stream()
                .filter(j -> j.getSkillsList() != null)
                .flatMap(j -> j.getSkillsList().stream())
                .map(skill -> skill.getName().toLowerCase())
                .collect(Collectors.toSet());

        if (requiredSkills.isEmpty()) return 0.0;

        long missingSkills = requiredSkills.stream()
                .filter(skill -> !userSkills.contains(skill))
                .count();

        return (double) missingSkills / requiredSkills.size();
    }
}