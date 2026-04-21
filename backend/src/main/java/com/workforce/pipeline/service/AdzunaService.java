package com.workforce.pipeline.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AdzunaService {

    @Value("${adzuna.app.id}")
    private String appId;

    @Value("${adzuna.app.key}")
    private String appKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // Common tech skills for keyword extraction from job descriptions
    private static final List<String> TECH_SKILLS = List.of(
        "Java", "Python", "JavaScript", "TypeScript", "React", "Angular", "Vue",
        "Node.js", "Spring Boot", "Spring", "SQL", "PostgreSQL", "MySQL", "MongoDB",
        "AWS", "Azure", "GCP", "Docker", "Kubernetes", "Git", "REST API", "GraphQL",
        "Microservices", "CI/CD", "Jenkins", "Terraform", "Linux", "Bash",
        "C#", ".NET", "PHP", "Ruby", "Go", "Rust", "Scala", "Kafka", "Redis",
        "Elasticsearch", "Spark", "Hadoop", "TensorFlow", "PyTorch",
        "Machine Learning", "Data Science", "NLP", "Agile", "Scrum", "Tableau",
        "Power BI", "Airflow", "dbt", "Snowflake", "Databricks"
    );

    public List<Map<String, Object>> fetchJobs(String query, String location) {
        try {
            String encodedQuery = URLEncoder.encode(query != null ? query : "", StandardCharsets.UTF_8);
            String encodedLocation = URLEncoder.encode(location != null ? location : "", StandardCharsets.UTF_8);

            String url = String.format(
                "https://api.adzuna.com/v1/api/jobs/us/search/1?app_id=%s&app_key=%s&what=%s&where=%s&results_per_page=20",
                appId, appKey, encodedQuery, encodedLocation
            );

            System.out.println("🌐 CALLING ADZUNA: " + url);

            String response = restTemplate.getForObject(url, String.class);

            if (response == null || response.isEmpty()) {
                System.out.println("❌ EMPTY RESPONSE FROM ADZUNA");
                return new ArrayList<>();
            }

            JsonNode root = mapper.readTree(response);
            JsonNode results = root.path("results");

            if (!results.isArray() || results.size() == 0) {
                System.out.println("⚠ NO JOB RESULTS FROM ADZUNA");
                return new ArrayList<>();
            }

            List<Map<String, Object>> jobs = new ArrayList<>();

            for (JsonNode job : results) {
                Map<String, Object> cleaned = new HashMap<>();

                // --- Core fields ---
                cleaned.put("id", job.path("id").asText(""));
                cleaned.put("title", job.path("title").asText("Untitled"));
                cleaned.put("description", job.path("description").asText(""));

                // --- Nested object fields (the parsing fix) ---
                cleaned.put("company", job.path("company").path("display_name").asText("Unknown Company"));
                cleaned.put("location", job.path("location").path("display_name").asText("Unknown Location"));

                // --- Date from "created" field ---
                cleaned.put("fetchedAt", job.path("created").asText(""));

                // --- Salary range formatted as "$X-$Y" ---
                double salaryMin = job.path("salary_min").asDouble(0);
                double salaryMax = job.path("salary_max").asDouble(0);
                String salaryRange = formatSalaryRange(salaryMin, salaryMax);
                cleaned.put("salaryRange", salaryRange);

                // --- Extract tech skills from description ---
                String description = (String) cleaned.get("description");
                String extractedSkills = extractSkills(description);
                cleaned.put("requiredSkills", extractedSkills);

                jobs.add(cleaned);
            }

            System.out.println("✅ ADZUNA FETCH COMPLETE — jobs parsed: " + jobs.size());
            return jobs;

        } catch (Exception e) {
            System.err.println("❌ Adzuna fetch failed: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String formatSalaryRange(double min, double max) {
        if (min <= 0 && max <= 0) return "Salary not listed";
        if (min <= 0) return String.format("Up to $%,.0f", max);
        if (max <= 0) return String.format("From $%,.0f", min);
        return String.format("$%,.0f–$%,.0f", min, max);
    }

    private String extractSkills(String description) {
        if (description == null || description.isEmpty()) return "";
        String lower = description.toLowerCase();
        List<String> found = new ArrayList<>();
        for (String skill : TECH_SKILLS) {
            if (lower.contains(skill.toLowerCase())) {
                found.add(skill);
            }
        }
        return String.join(", ", found);
    }
}
