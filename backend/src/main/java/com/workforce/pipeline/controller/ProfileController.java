package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.SkillProfile;
import com.workforce.pipeline.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // GET /api/profiles/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Integer userId) {
        SkillProfile profile = profileService.getByUserId(userId);
        if (profile == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(profile);
    }

    // PUT /api/profiles/{userId}
    @PutMapping("/{userId}")
    public ResponseEntity<SkillProfile> saveProfile(
            @PathVariable Integer userId,
            @RequestBody SkillProfile incoming
    ) {
        SkillProfile saved = profileService.save(userId, incoming);
        return ResponseEntity.ok(saved);
    }
}
