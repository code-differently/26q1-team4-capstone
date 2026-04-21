package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.EmployerPosting;
import com.workforce.pipeline.model.SkillProfile;
import com.workforce.pipeline.model.TrainingOffering;
import com.workforce.pipeline.repository.EmployerPostingRepository;
import com.workforce.pipeline.repository.TrainingOfferingRepository;
import com.workforce.pipeline.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/directory")
public class DirectoryController {

    private final TrainingOfferingRepository trainingRepo;
    private final EmployerPostingRepository employerRepo;
    private final ProfileService profileService;

    public DirectoryController(
            TrainingOfferingRepository trainingRepo,
            EmployerPostingRepository employerRepo,
            ProfileService profileService
    ) {
        this.trainingRepo = trainingRepo;
        this.employerRepo = employerRepo;
        this.profileService = profileService;
    }

    // GET /api/directory/training
    @GetMapping("/training")
    public ResponseEntity<List<TrainingOffering>> getTraining() {
        return ResponseEntity.ok(trainingRepo.findAll());
    }

    // POST /api/directory/training
    @PostMapping("/training")
    public ResponseEntity<TrainingOffering> postTraining(@RequestBody TrainingOffering offering) {
        offering.setCreatedAt(new Date());
        return ResponseEntity.ok(trainingRepo.save(offering));
    }

    // GET /api/directory/employers
    @GetMapping("/employers")
    public ResponseEntity<List<EmployerPosting>> getEmployers() {
        return ResponseEntity.ok(employerRepo.findAll());
    }

    // POST /api/directory/employers
    @PostMapping("/employers")
    public ResponseEntity<EmployerPosting> postEmployer(@RequestBody EmployerPosting posting) {
        posting.setCreatedAt(new Date());
        return ResponseEntity.ok(employerRepo.save(posting));
    }

    // GET /api/directory/talent?skill=...
    @GetMapping("/talent")
    public ResponseEntity<List<SkillProfile>> getTalent(
            @RequestParam(defaultValue = "") String skill
    ) {
        return ResponseEntity.ok(profileService.searchDiscoverable(skill));
    }
}
