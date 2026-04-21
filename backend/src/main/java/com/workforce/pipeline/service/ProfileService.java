package com.workforce.pipeline.service;

import com.workforce.pipeline.model.SkillProfile;
import com.workforce.pipeline.repository.SkillProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProfileService {

    private final SkillProfileRepository skillProfileRepository;

    public ProfileService(SkillProfileRepository skillProfileRepository) {
        this.skillProfileRepository = skillProfileRepository;
    }

    public SkillProfile getByUserId(Integer userId) {
        return skillProfileRepository.findByUserId(userId).orElse(null);
    }

    public SkillProfile save(Integer userId, SkillProfile incoming) {
        SkillProfile profile = skillProfileRepository.findByUserId(userId)
                .orElse(new SkillProfile());

        profile.setUserId(userId);
        if (incoming.getSkills() != null) profile.setSkills(incoming.getSkills());
        if (incoming.getTargetRole() != null) profile.setTargetRole(incoming.getTargetRole());
        if (incoming.getExperienceLevel() != null) profile.setExperienceLevel(incoming.getExperienceLevel());
        if (incoming.getIsDiscoverable() != null) profile.setIsDiscoverable(incoming.getIsDiscoverable());
        profile.setUpdatedAt(new Date());

        return skillProfileRepository.save(profile);
    }

    public List<SkillProfile> searchDiscoverable(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return skillProfileRepository.findByIsDiscoverableTrue();
        }
        return skillProfileRepository.findBySkillsContainingIgnoreCaseAndIsDiscoverableTrue(keyword);
    }
}
