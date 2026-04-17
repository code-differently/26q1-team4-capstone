package com.workforce.pipeline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.TrainingProgram;
import com.workforce.pipeline.service.TrainingService;
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

@WebMvcTest(TrainingController.class)
class TrainingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrainingService trainingService;

    @Test
    void createProgramReturnsSavedProgram() throws Exception {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend training");
        when(trainingService.createTrainingProgram(any(TrainingProgram.class))).thenReturn(program);

        mockMvc.perform(post("/training")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(program)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bootcamp"));
    }

    @Test
    void getAllProgramsReturnsList() throws Exception {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend training");
        when(trainingService.getAllPrograms()).thenReturn(List.of(program));

        mockMvc.perform(get("/training"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bootcamp"));
    }

    @Test
    void addSkillDelegatesSkillIdToService() throws Exception {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend training");
        Skill skill = new Skill();
        skill.setId(7);
        when(trainingService.addSkillToProgram(1, 7)).thenReturn(program);

        mockMvc.perform(post("/training/1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":7}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bootcamp"));

        verify(trainingService).addSkillToProgram(1, 7);
    }

    @Test
    void deleteProgramInvokesService() throws Exception {
        doNothing().when(trainingService).deleteProgram(2);

        mockMvc.perform(delete("/training/2"))
                .andExpect(status().isOk());

        verify(trainingService).deleteProgram(2);
    }
}
