package com.workforce.pipeline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workforce.pipeline.dto.UserDTO;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void createUserReturnsSavedUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setRole("JOB_SEEKER");

        when(userService.createUser(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    void getUserSkillsReturnsSkillsForUser() throws Exception {
        Skill skill = new Skill();
        skill.setId(4);
        skill.setName("Java");

        when(userService.getUserSkills(1)).thenReturn(List.of(skill));

        mockMvc.perform(get("/users/1/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[0].name").value("Java"));
    }

    @Test
    void addSkillToUserDelegatesSkillIdToService() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setName("Alice");

        when(userService.addSkillToUser(1, 9)).thenReturn(user);

        mockMvc.perform(post("/users/1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":9}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(userService).addSkillToUser(1, 9);
    }

    @Test
    void updateUserReturnsUpdatedUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(2);
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setRole("EMPLOYER");

        when(userService.updateUser(any(Integer.class), any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(put("/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.role").value("EMPLOYER"));
    }
}