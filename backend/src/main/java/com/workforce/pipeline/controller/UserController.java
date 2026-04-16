package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    // Service layer handles business logic
    @Autowired
    private UserService userService;

    // ----------------------------
    // CREATE USER ENDPOINT
    // POST /users
    // Creates a new user in system
    // ----------------------------
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // ----------------------------
    // GET ALL USERS
    // GET /users
    // Returns list of all users
    // ----------------------------
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ----------------------------
    // GET USER BY ID
    // GET /users/{id}
    // Fetch single user
    // ----------------------------
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // ----------------------------
    // GET USER SKILLS
    // GET /users/{id}/skills
    // Used for:
    // - Job seeker profile
    // - gap analysis
    // - recommendation system
    // ----------------------------
    @GetMapping("/{id}/skills")
    public List<?> getUserSkills(@PathVariable Integer id) {
        return userService.getUserSkills(id);
    }

    // ----------------------------
    // ADD SKILL TO USER
    // POST /users/{id}/skills
    // Adds skill to a specific user
    // ----------------------------
    @PostMapping("/{id}/skills")
    public User addSkillToUser(@PathVariable Integer id, @RequestBody Skill skill) {
        return userService.addSkillToUser(id, skill);
    }
}