package com.workforce.pipeline.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class RoleTest {

    @Test
    void settersUpdateRoleFields() {
        Role role = new Role();

        role.setId(2);
        role.setTitle("Software Engineer");
        role.setRegion("Northeast");
        role.setIndustry("Technology");
        role.setDemandScore(12.5);

        assertEquals(2, role.getId());
        assertEquals("Software Engineer", role.getTitle());
        assertEquals("Northeast", role.getRegion());
        assertEquals("Technology", role.getIndustry());
        assertEquals(12.5, role.getDemandScore());
    }

    @Test
    void jobsListCanBeAssigned() {
        Role role = new Role();
        Job job = new Job();
        job.setTitle("Backend Developer");
        List<Job> jobs = List.of(job);

        role.setJobs(jobs);

        assertSame(jobs, role.getJobs());
    }
}
