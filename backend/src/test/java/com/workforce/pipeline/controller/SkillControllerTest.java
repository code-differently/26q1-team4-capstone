package com.workforce.pipeline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workforce.pipeline.enums.DemandLevel;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SkillService skillService;

    @Test
    void createSkillReturnsSavedSkill() throws Exception {
        Skill skill = new Skill();
        skill.setId(3);
        skill.setName("Java");
        when(skillService.saveSkill(any(Skill.class))).thenReturn(skill);

        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skill)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("java"));
    }

    @Test
    void getAllSkillsReturnsList() throws Exception {
        Skill skill = new Skill();
        skill.setId(4);
        skill.setName("Python");
        when(skillService.getAllSkills()).thenReturn(List.of(skill));

        mockMvc.perform(get("/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[0].name").value("python"));
    }

    @Test
    void updateSkillReturnsUpdatedSkill() throws Exception {
        Skill skill = new Skill();
        skill.setId(2);
        skill.setName("Spring");
        skill.setDemandLevel(DemandLevel.HIGH);
        when(skillService.updateSkill(any(Integer.class), any(Skill.class))).thenReturn(skill);

        mockMvc.perform(put("/skills/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skill)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.demandLevel").value("HIGH"));
    }

    @Test
    void deleteSkillInvokesService() throws Exception {
        doNothing().when(skillService).deleteSkill(8);

        mockMvc.perform(delete("/skills/8"))
                .andExpect(status().isOk());

        verify(skillService).deleteSkill(8);
    }
}
