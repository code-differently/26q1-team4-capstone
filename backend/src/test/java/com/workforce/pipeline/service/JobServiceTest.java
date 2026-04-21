package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Role;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.RoleRepository;
import com.workforce.pipeline.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    void createJobSetsDateWhenMissing() {
        Job job = new Job();
        when(jobRepository.save(any(Job.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Job savedJob = jobService.createJob(job);

        assertNotNull(savedJob.getDatePosted());
        verify(jobRepository).save(job);
    }

    @Test
    void updateJobReturnsNullWhenJobDoesNotExist() {
        when(jobRepository.findById(99)).thenReturn(Optional.empty());

        Job updatedJob = jobService.updateJob(99, new Job());

        assertNull(updatedJob);
        verify(jobRepository, never()).save(any(Job.class));
    }

    @Test
    void addSkillToJobDoesNotDuplicateExistingSkill() {
        Job job = new Job();
        job.setSkillsList(new ArrayList<>());
        Skill skill = new Skill();
        skill.setId(5);
        skill.setName("Java");
        job.addSkill(skill);

        when(jobRepository.findById(1)).thenReturn(Optional.of(job));
        when(skillRepository.findById(5)).thenReturn(Optional.of(skill));
        when(jobRepository.save(any(Job.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Job updatedJob = jobService.addSkillToJob(1, 5);

        assertEquals(1, updatedJob.getSkillsList().size());
    }

    @Test
    void assignRoleToJobSetsRoleAndSaves() {
        Job job = new Job();
        Role role = new Role();
        role.setId(3);

        when(jobRepository.findById(1)).thenReturn(Optional.of(job));
        when(roleRepository.findById(3)).thenReturn(Optional.of(role));
        when(jobRepository.save(any(Job.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Job updatedJob = jobService.assignRoleToJob(1, 3);

        assertEquals(role, updatedJob.getRole());
        verify(jobRepository).save(job);
    }

    @Test
    void deleteJobReturnsFalseWhenJobMissing() {
        when(jobRepository.existsById(10)).thenReturn(false);

        boolean deleted = jobService.deleteJob(10);

        assertEquals(false, deleted);

        verify(jobRepository, never()).deleteById(10);
    }
}
