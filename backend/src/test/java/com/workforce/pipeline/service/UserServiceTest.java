package com.workforce.pipeline.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        User updatedUser = userService.updateUser(12, new User());

        assertNull(updatedUser);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUserCopiesFieldsAndSaves() {
        User existingUser = new User("Alice", "a@example.com", "old", UserRole.JOB_SEEKER);
        User incomingUser = new User("Bob", "b@example.com", "new", UserRole.EMPLOYER);

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.updateUser(1, incomingUser);

        assertEquals("Bob", updatedUser.getName());
        assertEquals("b@example.com", updatedUser.getEmail());
        assertEquals("new", updatedUser.getPassword());
        assertEquals(UserRole.EMPLOYER, updatedUser.getRole());
    }

    @Test
    void addSkillToUserAddsSkillAndSaves() {
        User user = new User("Alice", "a@example.com", "pw", UserRole.JOB_SEEKER);
        Skill skill = new Skill();
        skill.setId(5);
        skill.setName("Java");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(skillRepository.findById(5)).thenReturn(Optional.of(skill));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.addSkillToUser(1, 5);

        assertEquals(1, updatedUser.getSkills().size());
        assertEquals("java", updatedUser.getSkills().getFirst().getName());
    }

    @Test
    void addSkillToUserReturnsNullWhenSkillMissing() {
        User user = new User("Alice", "a@example.com", "pw", UserRole.JOB_SEEKER);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(skillRepository.findById(7)).thenReturn(Optional.empty());

        User updatedUser = userService.addSkillToUser(1, 7);

        assertNull(updatedUser);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void removeSkillFromUserRemovesMatchingSkill() {
        User user = new User("Alice", "a@example.com", "pw", UserRole.JOB_SEEKER);
        Skill skill = new Skill();
        skill.setId(5);
        skill.setName("Java");
        user.addSkill(skill);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.removeSkillFromUser(1, 5);

        assertEquals(0, updatedUser.getSkills().size());
    }
}
