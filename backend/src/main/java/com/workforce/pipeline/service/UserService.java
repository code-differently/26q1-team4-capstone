package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.repository.SkillRepository;
import com.workforce.pipeline.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserService(UserRepository userRepository, SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    // CREATE
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // READ ALL
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ ONE
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // UPDATE (FIX FOR YOUR ERROR)
    public User updateUser(Integer id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());

        return userRepository.save(user);
    }

    // DELETE (FIX FOR YOUR ERROR)
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // GET SKILLS
    public List<Skill> getUserSkills(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;

        return user.getSkills();
    }

    // ADD SKILL (FIX TYPE ISSUE)
    public User addSkillToUser(Integer userId, Integer skillId) {
        User user = userRepository.findById(userId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);

        if (user == null || skill == null) return null;

        user.getSkills().add(skill);
        return userRepository.save(user);
    }

    // REMOVE SKILL
    public User removeSkillFromUser(Integer userId, Integer skillId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        user.getSkills().removeIf(skill -> skill.getId() == skillId);
        return userRepository.save(user);
    }
}