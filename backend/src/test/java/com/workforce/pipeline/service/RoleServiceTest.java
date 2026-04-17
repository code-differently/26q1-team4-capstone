package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Role;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void createRoleForcesDemandScoreToZero() {
        Role role = new Role();
        role.setDemandScore(99.0);
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Role savedRole = roleService.createRole(role);

        assertEquals(0.0, savedRole.getDemandScore());
    }

    @Test
    void updateRoleThrowsWhenRoleMissing() {
        when(roleRepository.findById(9)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.updateRole(9, new Role()));
    }

    @Test
    void updateRoleChangesEditableFieldsWithoutTouchingDemandScore() {
        Role existingRole = new Role();
        existingRole.setDemandScore(7.5);
        existingRole.setTitle("Old");
        existingRole.setRegion("Old Region");
        existingRole.setIndustry("Old Industry");

        Role incomingRole = new Role();
        incomingRole.setTitle("New");
        incomingRole.setRegion("New Region");
        incomingRole.setIndustry("New Industry");
        incomingRole.setDemandScore(100.0);

        when(roleRepository.findById(1)).thenReturn(Optional.of(existingRole));
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Role updatedRole = roleService.updateRole(1, incomingRole);

        assertEquals("New", updatedRole.getTitle());
        assertEquals("New Region", updatedRole.getRegion());
        assertEquals("New Industry", updatedRole.getIndustry());
        assertEquals(7.5, updatedRole.getDemandScore());
    }

    @Test
    void refreshDemandScoreCountsJobsForRole() {
        Role role = new Role();
        role.setId(4);
        when(roleRepository.findById(4)).thenReturn(Optional.of(role));
        when(jobRepository.countByRole_Id(4)).thenReturn(11L);
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Role refreshedRole = roleService.refreshDemandScore(4);

        assertEquals(11.0, refreshedRole.getDemandScore());
    }

    @Test
    void deleteRoleReturnsFalseWhenRoleMissing() {
        when(roleRepository.existsById(8)).thenReturn(false);

        boolean deleted = roleService.deleteRole(8);

        assertFalse(deleted);
        verify(roleRepository, never()).deleteById(8);
    }
}
