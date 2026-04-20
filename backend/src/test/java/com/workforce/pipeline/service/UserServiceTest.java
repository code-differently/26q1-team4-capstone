package com.workforce.pipeline.service;

import com.workforce.pipeline.dto.UserDTO;
import com.workforce.pipeline.enums.UserRole;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.User;
import com.workforce.pipeline.repository.SkillRepository;
import com.workforce.pipeline.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void updateUserReturnsNullWhenUserMissing() {
        when(userRepository.findById(12)).thenReturn(Optional.empty());

        UserDTO dto = new UserDTO();
        UserDTO result = userService.updateUser(12, dto);

        assertNull(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserCopiesFieldsAndSaves() {
        User existingUser = new User("Alice", "a@example.com", "old", UserRole.JOB_SEEKER);

        UserDTO dto = new UserDTO();
        dto.setName("Bob");
        dto.setEmail("b@example.com");
        dto.setPassword("new");
        dto.setRole("EMPLOYER");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserDTO updated = userService.updateUser(1, dto);

        assertEquals("Bob", updated.getName());
        assertEquals("b@example.com", updated.getEmail());
        assertEquals("EMPLOYER", updated.getRole());
    }

    @Test
    void addSkillToUserAddsSkillAndSaves() {
        User user = new User("Alice", "a@example.com", "pw", UserRole.JOB_SEEKER);

        Skill skill = new Skill();
        skill.setId(5);
        skill.setName("Java");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(skillRepository.findById(5)).thenReturn(Optional.of(skill));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserDTO updated = userService.addSkillToUser(1, 5);

        assertEquals(1, updated.getSkills().size());
    }

    @Test
    void addSkillToUserReturnsNullWhenSkillMissing() {
        User user = new User("Alice", "a@example.com", "pw", UserRole.JOB_SEEKER);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(skillRepository.findById(7)).thenReturn(Optional.empty());

        UserDTO result = userService.addSkillToUser(1, 7);

        assertNull(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void removeSkillFromUserRemovesMatchingSkill() {
        User user = new User("Alice", "a@example.com", "pw", UserRole.JOB_SEEKER);

        Skill skill = new Skill();
        skill.setId(5);
        skill.setName("Java");
        user.addSkill(skill);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserDTO updated = userService.removeSkillFromUser(1, 5);

        assertEquals(0, updated.getSkills().size());
    }
}