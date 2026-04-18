package com.workforce.pipeline.service;

import com.workforce.pipeline.dto.UserDTO;
import com.workforce.pipeline.enums.UserRole;
import com.workforce.pipeline.mapper.UserMapper;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.repository.SkillRepository;
import com.workforce.pipeline.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserService(UserRepository userRepository, SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    // CREATE
    public UserDTO createUser(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        return UserMapper.toDTO(userRepository.save(user));
    }

    // READ ALL
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    // READ ONE
    public UserDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElse(null);
    }

    // UPDATE
    public UserDTO updateUser(Integer id, UserDTO dto) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(UserRole.valueOf(dto.getRole()));

        return UserMapper.toDTO(userRepository.save(user));
    }

    // DELETE
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // ADD SKILL
    public UserDTO addSkillToUser(Integer userId, Integer skillId) {

        User user = userRepository.findById(userId).orElse(null);
        Skill skill = skillRepository.findById(skillId).orElse(null);

        if (user == null || skill == null) return null;

        user.getSkills().add(skill);

        return UserMapper.toDTO(userRepository.save(user));
    }

    // REMOVE SKILL (FIXED)
    public UserDTO removeSkillFromUser(Integer userId, Integer skillId) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        user.getSkills().removeIf(skill -> skill.getId() == skillId);

        return UserMapper.toDTO(userRepository.save(user));
    }

    // GET SKILLS (returns full Skill objects)
    public List<Skill> getUserSkills(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return List.of();

        return user.getSkills();
    }
}