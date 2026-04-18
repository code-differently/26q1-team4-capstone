package com.workforce.pipeline.controller;

import com.workforce.pipeline.dto.UserDTO;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController
 *
 * PURPOSE:
 * Handles all HTTP requests related to Users.
 *
 * DESIGN CHOICE:
 * Uses DTOs instead of Entities to:
 * - Prevent Hibernate lazy-loading JSON errors
 * - Avoid infinite recursion in relationships
 * - Ensure clean API responses for frontend + Postman
 * - Decouple database structure from API layer
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // ----------------------------------------
    // DEPENDENCY INJECTION
    // ----------------------------------------
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ----------------------------------------
    // CREATE USER
    // POST /users
    //
    // Creates a new user in the database.
    // Returns UserDTO (safe API response format).
    // ----------------------------------------
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }

    // ----------------------------------------
    // GET ALL USERS
    // GET /users
    //
    // Returns all users in DTO format.
    // No entity exposure → prevents lazy loading issues.
    // ----------------------------------------
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // ----------------------------------------
    // GET USER BY ID
    // GET /users/{id}
    //
    // Returns a single user in DTO format.
    // ----------------------------------------
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // ----------------------------------------
    // UPDATE USER
    // PUT /users/{id}
    //
    // Updates user fields (name, email, role, password).
    // Returns updated DTO.
    // ----------------------------------------
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Integer id,
                              @RequestBody UserDTO dto) {
        return userService.updateUser(id, dto);
    }

    // ----------------------------------------
    // DELETE USER
    // DELETE /users/{id}
    //
    // Removes user from database.
    // Returns void (no DTO needed).
    // ----------------------------------------
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    // ----------------------------------------
    // GET USER SKILLS
    // GET /users/{id}/skills
    //
    // Returns raw Skill entities (simple list only).
    // Kept as entity because it's a flat relationship.
    // ----------------------------------------
    @GetMapping("/{id}/skills")
    public List<Skill> getUserSkills(@PathVariable Integer id) {
        return userService.getUserSkills(id);
    }

    // ----------------------------------------
    // ADD SKILL TO USER
    // POST /users/{id}/skills
    //
    // Adds a skill to user by skill ID.
    // Returns updated UserDTO.
    // ----------------------------------------
    @PostMapping("/{id}/skills")
    public UserDTO addSkillToUser(@PathVariable Integer id,
                                  @RequestBody Skill skill) {
        return userService.addSkillToUser(id, skill.getId());
    }

    // ----------------------------------------
    // REMOVE SKILL FROM USER
    // DELETE /users/{id}/skills/{skillId}
    //
    // Removes skill from user.
    // Returns updated UserDTO.
    // ----------------------------------------
    @DeleteMapping("/{id}/skills/{skillId}")
    public UserDTO removeSkillFromUser(@PathVariable Integer id,
                                       @PathVariable Integer skillId) {
        return userService.removeSkillFromUser(id, skillId);
    }
}