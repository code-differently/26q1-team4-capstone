package com.workforce.pipeline.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RecommendationService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> generateRecommendations(
            List<String> userSkills,
            List<String> jobSkills,
            String jobTitle
    ) {

        try {
            String prompt = buildPrompt(userSkills, jobSkills, jobTitle);

            Map<String, Object> request = new HashMap<>();
            request.put("model", "gpt-4o-mini");

            request.put("messages", List.of(
                    Map.of("role", "system",
                            "content", "You are a workforce intelligence engine. You MUST return structured JSON only."),
                    Map.of("role", "user", "content", prompt)
            ));

            request.put("temperature", 0.3);

            String response = restTemplate.postForObject(
                    "https://api.openai.com/v1/chat/completions",
                    request,
                    String.class
            );

            return objectMapper.readValue(response, Map.class);

        } catch (Exception e) {
            throw new RuntimeException("AI recommendation failed", e);
        }
    }

    private String buildPrompt(List<String> userSkills,
                               List<String> jobSkills,
                               String jobTitle) {

        return """
        You are an explainable workforce intelligence system.

        TASK:
        Analyze job match and skill gaps.

        OUTPUT MUST BE VALID JSON:

        {
          "matchScore": number,
          "missingSkills": [],
          "recommendation": "",
          "trainingSuggestions": []
        }

        USER SKILLS:
        %s

        JOB TITLE:
        %s

        REQUIRED JOB SKILLS:
        %s

        RULES:
        - Be specific
        - No generic advice
        - Explain real skill gaps
        """.formatted(userSkills, jobTitle, jobSkills);
    }
}