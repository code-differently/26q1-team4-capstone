package com.workforce.pipeline.service;

import com.workforce.pipeline.enums.DemandLevel;
import com.workforce.pipeline.model.Job;
import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.repository.JobRepository;
import com.workforce.pipeline.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private SkillService skillService;

    @Test
    void createSkillReturnsExistingSkillWhenDuplicateNameFound() {
        Skill inputSkill = new Skill();
        inputSkill.setName(" Java ");
        Skill existingSkill = new Skill();
        existingSkill.setId(4);
        existingSkill.setName("java");

        when(skillRepository.existsByNameIgnoreCase("java")).thenReturn(true);
        when(skillRepository.findByNameIgnoreCase("java")).thenReturn(Optional.of(existingSkill));

        Skill savedSkill = skillService.saveSkill(inputSkill);

        assertEquals(existingSkill, savedSkill);
        verify(skillRepository, never()).save(any(Skill.class));
    }

    @Test
    void saveSkillPersistsNewSkill() {
        Skill skill = new Skill();
        skill.setName("Python");
        when(skillRepository.existsByNameIgnoreCase("python")).thenReturn(false);
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill savedSkill = skillService.saveSkill(skill);

        assertEquals("python", savedSkill.getName());
        verify(skillRepository).save(skill);
    }

    @Test
    void updateSkillReturnsNullWhenSkillMissing() {
        when(skillRepository.findById(7)).thenReturn(Optional.empty());

        Skill updatedSkill = skillService.updateSkill(7, new Skill());

        assertNull(updatedSkill);
        verify(skillRepository, never()).save(any(Skill.class));
    }

    @Test
    void updateSkillCopiesMutableFields() {
        Skill existingSkill = new Skill();
        existingSkill.setId(7);
        existingSkill.setName("java");
        Skill incomingSkill = new Skill();
        incomingSkill.setName("Spring");
        incomingSkill.setDemandLevel(DemandLevel.HIGH);

        when(skillRepository.findById(7)).thenReturn(Optional.of(existingSkill));
        when(skillRepository.save(any(Skill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Skill updatedSkill = skillService.updateSkill(7, incomingSkill);

        assertEquals("spring", updatedSkill.getName());
        assertEquals(DemandLevel.HIGH, updatedSkill.getDemandLevel());
    }

    @Test
    void deleteSkillDelegatesToRepository() {
        skillService.deleteSkill(9);

        verify(skillRepository).deleteById(9);
    }
}
