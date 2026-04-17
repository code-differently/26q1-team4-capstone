package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // READ ALL
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // READ ONE
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    // GET SKILLS
    @GetMapping("/{id}/skills")
    public List<Skill> getUserSkills(@PathVariable Integer id) {
        return userService.getUserSkills(id);
    }

    // ADD SKILL (STANDARDIZED)
    @PostMapping("/{id}/skills")
    public User addSkillToUser(@PathVariable Integer id, @RequestBody Skill skill) {
        return userService.addSkillToUser(id, skill.getId());
    }

    // REMOVE SKILL
    @DeleteMapping("/{id}/skills/{skillId}")
    public User removeSkillFromUser(@PathVariable Integer id, @PathVariable Integer skillId) {
        return userService.removeSkillFromUser(id, skillId);
    }
}