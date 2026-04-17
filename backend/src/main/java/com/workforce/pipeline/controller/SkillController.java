package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.service.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillService.createSkill(skill);
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public Skill getSkillById(@PathVariable Integer id) {
        return skillService.getSkillById(id);
    }

    @GetMapping("/search")
    public Skill getSkillByName(@RequestParam String name) {
        return skillService.getSkillByName(name);
    }

    @PutMapping("/{id}")
    public Skill updateSkill(@PathVariable Integer id, @RequestBody Skill skill) {
        return skillService.updateSkill(id, skill);
    }

    @PutMapping("/{id}/refresh-demand")
    public Skill refreshDemandLevel(@PathVariable Integer id) {
        return skillService.refreshDemandLevel(id);
    }
}
