package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Role;
import com.workforce.pipeline.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    @GetMapping("/search/region")
    public List<Role> getRolesByRegion(@RequestParam String region) {
        return roleService.getRolesByRegion(region);
    }

    @GetMapping("/search/industry")
    public List<Role> getRolesByIndustry(@RequestParam String industry) {
        return roleService.getRolesByIndustry(industry);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Integer id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    @PutMapping("/{id}/refresh-demand")
    public Role refreshDemandScore(@PathVariable Integer id) {
        return roleService.refreshDemandScore(id);
    }
}
