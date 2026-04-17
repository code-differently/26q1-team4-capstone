package com.workforce.pipeline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workforce.pipeline.model.Role;
import com.workforce.pipeline.service.RoleService;
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

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleService roleService;

    @Test
    void createRoleReturnsSavedRole() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setTitle("Software Engineer");
        when(roleService.createRole(any(Role.class))).thenReturn(role);

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Software Engineer"));
    }

    @Test
    void getAllRolesReturnsList() throws Exception {
        Role role = new Role();
        role.setId(2);
        role.setTitle("Analyst");
        when(roleService.getAllRoles()).thenReturn(List.of(role));

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].title").value("Analyst"));
    }

    @Test
    void refreshDemandScoreReturnsRoleFromService() throws Exception {
        Role role = new Role();
        role.setId(3);
        role.setDemandScore(8.0);
        when(roleService.refreshDemandScore(3)).thenReturn(role);

        mockMvc.perform(put("/roles/3/refresh-demand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.demandScore").value(8.0));
    }

    @Test
    void deleteRoleDelegatesToService() throws Exception {
        when(roleService.deleteRole(4)).thenReturn(true);

        mockMvc.perform(delete("/roles/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(roleService).deleteRole(4);
    }
}
