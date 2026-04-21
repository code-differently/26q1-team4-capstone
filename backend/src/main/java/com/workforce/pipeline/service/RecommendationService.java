package com.workforce.pipeline.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.SkillProfile;
import com.workforce.pipeline.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Value("${claude.api.key}")
    private String claudeApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String CLAUDE_API_URL = "https://api.anthropic.com/v1/messages";
    private static final String CLAUDE_MODEL = "claude-sonnet-4-6";

    // =========================
    // MAIN ENTRY POINT
    // =========================
    public String generateRecommendation(User user, SkillProfile profile, List<Job> jobs) {
        String prompt = buildPrompt(user, profile, jobs);
        return callClaudeApi(prompt);
    }

    // =========================
    // CLAUDE API CALL
    // =========================
    private String callClaudeApi(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", claudeApiKey);
            headers.set("anthropic-version", "2023-06-01");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", CLAUDE_MODEL);
            requestBody.put("max_tokens", 2048);
            requestBody.put("messages", List.of(
                    Map.of("role", "user", "content", prompt)
            ));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    CLAUDE_API_URL, HttpMethod.POST, entity, String.class
            );

            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path("content").get(0).path("text").asText();

        } catch (Exception e) {
            System.err.println("❌ Claude API call failed: " + e.getMessage());
            throw new RuntimeException("AI recommendation failed: " + e.getMessage());
        }
    }

    // =========================
    // PROMPT BUILDER
    // Uses the full Nodus workforce intelligence prompt template
    // =========================
    public String buildPrompt(User user, SkillProfile profile, List<Job> jobs) {
        int jobCount = jobs.size();
        String targetRole = (profile != null && profile.getTargetRole() != null)
                ? profile.getTargetRole()
                : jobs.isEmpty() ? "Software Engineer" : jobs.get(0).getTitle();

        String candidateName = user.getName() != null ? user.getName() : "Candidate";

        // Build skills string — prefer profile skills string, fall back to User.skills list
        String skills;
        if (profile != null && profile.getSkills() != null && !profile.getSkills().isBlank()) {
            skills = profile.getSkills();
        } else {
            skills = user.getSkills().stream()
                    .map(s -> s.getName())
                    .collect(Collectors.joining(", "));
        }
        if (skills.isBlank()) skills = "No skills listed yet";

        String experienceLevel = (profile != null && profile.getExperienceLevel() != null)
                ? profile.getExperienceLevel()
                : "Not specified";

        // Build job descriptions block
        StringBuilder jobDescriptions = new StringBuilder();
        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);
            jobDescriptions.append(String.format("Job %d: %s at %s (%s)%n",
                    i + 1,
                    job.getTitle() != null ? job.getTitle() : "Unknown Role",
                    job.getCompany() != null ? job.getCompany() : "Unknown Company",
                    job.getLocation() != null ? job.getLocation() : "Unknown Location"
            ));
            if (job.getRequiredSkills() != null && !job.getRequiredSkills().isBlank()) {
                jobDescriptions.append("  Required Skills: ").append(job.getRequiredSkills()).append("\n");
            }
            if (job.getSalaryRange() != null && !job.getSalaryRange().isBlank()) {
                jobDescriptions.append("  Salary: ").append(job.getSalaryRange()).append("\n");
            }
            if (job.getDescription() != null && !job.getDescription().isBlank()) {
                String desc = job.getDescription();
                if (desc.length() > 300) desc = desc.substring(0, 300) + "...";
                jobDescriptions.append("  Description: ").append(desc).append("\n");
            }
            jobDescriptions.append("\n");
        }

        return """
You are Nodus — an honest, direct workforce intelligence advisor. You analyze \
real job market data against a specific candidate profile and give guidance that \
is role-specific, evidence-based, and immediately actionable.

You are analyzing %d real job listings for "%s" against the following candidate profile.

CANDIDATE PROFILE:
Name: %s
Skills confirmed: %s
Projects built: Not specified
Experience level: %s
Notable background: Not provided

JOB LISTINGS ANALYZED:
%s
Respond in exactly this structure. Use only information found in the candidate \
profile and the job listings above — never invent skills, requirements, or \
recommendations that are not grounded in this specific data.

---

VERDICT: [Strong Match / Developing Match / Early Stage]
[One sentence — specific skill overlap from their actual profile against these \
actual listings. Never inflate a weak match. Be honest even if it is hard to hear.]

WHAT YOU BRING
[3-5 bullets — skills and projects they have that directly appear in these listings]
[Format: skill or project — why it matters for this specific role and company]

WHAT TO BUILD
[3-5 bullets — skills missing from their profile that these job listings require]
[Format: missing skill — which listing flagged it — why it matters for this role]

DIFFERENTIATORS
[2-3 bullets — non-technical strengths, background context, or unique angles \
from their profile that appear as valued qualities in these listings]
[Format: differentiator — which listing values it — how to surface it in \
an application or interview]

YOUR PATH FORWARD
[3-5 bullets — specific training recommendations tied only to gaps you identified above]
[Format: resource or action — realistic timeframe — which gap it closes]

BOTTOM LINE
[One direct closing sentence in mentor voice. Reference their experience level \
and their most compelling differentiator. Be encouraging but ruthlessly honest. \
Tell them exactly what to do first.]

---

Rules you must follow without exception:
Never mention skills not found in the job listings OR the candidate profile
Never give generic advice — "improve your LinkedIn" or "network more" are \
  not acceptable recommendations
Every bullet in YOUR PATH FORWARD must close a specific gap named in WHAT TO BUILD
The DIFFERENTIATORS section must surface non-obvious strengths that most \
  candidates at this experience level cannot claim
The VERDICT must be honest — do not inflate a weak match to protect feelings
Projects built are evidence of skill, not just claims — weight them accordingly
Keep each section tight — no padding, no filler, no repetition across sections
If the candidate's notable background is relevant to any listing, name it explicitly
""".formatted(jobCount, targetRole, candidateName, skills, experienceLevel, jobDescriptions.toString());
    }
}
