package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.service.AdzunaService;
import com.workforce.pipeline.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class NodusJobController {

    private final AdzunaService adzunaService;
    private final JobService jobService;

    public NodusJobController(AdzunaService adzunaService, JobService jobService) {
        this.adzunaService = adzunaService;
        this.jobService = jobService;
    }

    // GET /api/jobs/search?query=...&location=...
    // Fetches live results from Adzuna, saves new ones, returns full list
    @GetMapping("/search")
    public ResponseEntity<List<Job>> search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "") String location
    ) {
        List<Map<String, Object>> adzunaResults = adzunaService.fetchJobs(query, location);
        List<Job> imported = jobService.importFromAdzuna(adzunaResults);
        return ResponseEntity.ok(imported);
    }

    // GET /api/jobs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Job job = jobService.getJobById(id);
        if (job == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(job);
    }
}
