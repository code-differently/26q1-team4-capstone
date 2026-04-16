package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // Repository used to interact with database
    @Autowired
    private UserRepository userRepository;

    // ----------------------------
    // CREATE USER
    // Saves a new user into database
    // ----------------------------
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // ----------------------------
    // GET ALL USERS
    // Returns all users in system
    // ----------------------------
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ----------------------------
    // GET USER BY ID
    // Finds a user or returns null if not found
    // ----------------------------
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // ----------------------------
    // GET USER SKILLS
    // Returns list of skills for a given user
    // Used for Job Seeker profile + gap analysis logic
    // ----------------------------
    public List<?> getUserSkills(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;

        return user.getSkills();
    }

    // ----------------------------
    // ADD SKILL TO USER
    // Adds a skill and saves updated user
    // ----------------------------
    public User addSkillToUser(Integer id, Skill skill) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;

        user.addSkill(skill);
        return userRepository.save(user);
    }
}