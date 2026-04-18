package com.workforce.pipeline.mapper;

import com.workforce.pipeline.dto.JobDTO;
import com.workforce.pipeline.model.Job;

import java.util.stream.Collectors;

public class JobMapper {

    // Entity → DTO
    public static JobDTO toDTO(Job job) {
        if (job == null) return null;

        JobDTO dto = new JobDTO();

        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setDatePosted(job.getDatePosted());
        dto.setDataFreshness(job.getDataFreshness());

        if (job.getSkillsList() != null) {
            dto.setSkills(
                    job.getSkillsList()
                            .stream()
                            .map(skill -> skill.getName())
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    // DTO → Entity
    public static Job toEntity(JobDTO dto) {
        if (dto == null) return null;

        Job job = new Job();

        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setDatePosted(dto.getDatePosted());
        job.setDataFreshness(dto.getDataFreshness());

        return job;
    }
}