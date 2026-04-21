package com.workforce.pipeline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.service.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobController.class)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JobService jobService;

    @Test
    void createJobReturnsCreatedJobPayload() throws Exception {
        Job job = new Job();
        job.setId(1);
        job.setTitle("Backend Developer");
        job.setDatePosted(new Date());
        when(jobService.createJob(any(Job.class))).thenReturn(job);

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(job)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Backend Developer"));
    }

    @Test
    void getAllJobsReturnsListFromService() throws Exception {
        Job job = new Job();
        job.setId(2);
        job.setTitle("Data Analyst");
        when(jobService.getAllJobs()).thenReturn(List.of(job));

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].title").value("Data Analyst"));
    }

    @Test
    void addSkillDelegatesSkillIdToService() throws Exception {
        Job job = new Job();
        job.setId(1);
        Skill skill = new Skill();
        skill.setId(9);
        skill.setName("Java");
        when(jobService.addSkillToJob(1, 9)).thenReturn(job);

        mockMvc.perform(post("/jobs/1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":9}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(jobService).addSkillToJob(1, 9);
    }

    @Test
    void deleteJobInvokesService() throws Exception {

        doNothing().when(jobService).deleteJob(5);

        mockMvc.perform(delete("/jobs/5"))
                .andExpect(status().isOk());

        verify(jobService).deleteJob(5);
    }
}
