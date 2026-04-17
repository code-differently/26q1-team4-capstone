package com.workforce.pipeline.controller;

import com.workforce.pipeline.model.Role;
import com.workforce.pipeline.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // CREATE
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    // READ ALL
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // READ ONE
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    // SEARCH
    @GetMapping("/search/region")
    public List<Role> getRolesByRegion(@RequestParam String region) {
        return roleService.getRolesByRegion(region);
    }

    @GetMapping("/search/industry")
    public List<Role> getRolesByIndustry(@RequestParam String industry) {
        return roleService.getRolesByIndustry(industry);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Integer id,
                           @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    // REFRESH DEMAND
    @PutMapping("/{id}/refresh-demand")
    public Role refreshDemandScore(@PathVariable Integer id) {
        return roleService.refreshDemandScore(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
    }
}