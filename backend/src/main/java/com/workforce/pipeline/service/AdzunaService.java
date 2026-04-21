package com.workforce.pipeline.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AdzunaService {

    @Value("${adzuna.app.id}")
    private String appId;

    @Value("${adzuna.app.key}")
    private String appKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Map<String, Object>> fetchJobs(String query, String location) {

        try {
            String url = String.format(
                    "https://api.adzuna.com/v1/api/jobs/us/search/1?app_id=%s&app_key=%s&what=%s&where=%s",
                    appId,
                    appKey,
                    query,
                    location
            );

            String response = restTemplate.getForObject(url, String.class);

            JsonNode root = mapper.readTree(response);
            JsonNode results = root.get("results");

            List<Map<String, Object>> jobs = new ArrayList<>();

            for (JsonNode job : results) {
                Map<String, Object> cleaned = new HashMap<>();

                cleaned.put("title", job.get("title").asText());
                cleaned.put("description", job.get("description").asText());
                cleaned.put("company", job.path("company").path("display_name").asText(""));
                cleaned.put("location", job.path("location").path("display_name").asText(""));
                cleaned.put("created", job.get("created").asText());

                jobs.add(cleaned);
            }

            return jobs;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Adzuna jobs: " + e.getMessage());
        }
    }
}