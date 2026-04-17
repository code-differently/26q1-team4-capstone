package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    // ----------------------------------------
    // CREATE SKILL
    // POST /skills
    // ----------------------------------------
    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillService.saveSkill(skill);
    }

    // ----------------------------------------
    // GET ALL SKILLS
    // GET /skills
    // ----------------------------------------
    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    // ----------------------------------------
    // GET SKILL BY ID
    // GET /skills/{id}
    // ----------------------------------------
    @GetMapping("/{id}")
    public Skill getSkillById(@PathVariable int id) {
        return skillService.getSkillById(id);
    }

    // ----------------------------------------
    // UPDATE SKILL
    // PUT /skills/{id}
    // ----------------------------------------
    @PutMapping("/{id}")
    public Skill updateSkill(@PathVariable int id, @RequestBody Skill updatedSkill) {
        return skillService.updateSkill(id, updatedSkill);
    }

    // ----------------------------------------
    // DELETE SKILL
    // DELETE /skills/{id}
    // ----------------------------------------
    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable int id) {
        skillService.deleteSkill(id);
    }
}