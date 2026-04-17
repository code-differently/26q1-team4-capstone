package com.workforce.pipeline.service;

import com.workforce.pipeline.model.Skill;
import com.workforce.pipeline.model.TrainingProgram;
import com.workforce.pipeline.repository.SkillRepository;
import com.workforce.pipeline.repository.TrainingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private TrainingService trainingService;

    @Test
    void updateProgramReturnsNullWhenMissing() {
        when(trainingRepository.findById(6)).thenReturn(Optional.empty());

        TrainingProgram updatedProgram = trainingService.updateProgram(6, new TrainingProgram());

        assertNull(updatedProgram);
        verify(trainingRepository, never()).save(any(TrainingProgram.class));
    }

    @Test
    void updateProgramCopiesEditableFields() {
        TrainingProgram existing = new TrainingProgram("Old", "Old description");
        TrainingProgram incoming = new TrainingProgram("New", "New description");
        when(trainingRepository.findById(2)).thenReturn(Optional.of(existing));
        when(trainingRepository.save(any(TrainingProgram.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TrainingProgram updated = trainingService.updateProgram(2, incoming);

        assertEquals("New", updated.getName());
        assertEquals("New description", updated.getDescription());
    }

    @Test
    void addSkillToProgramReturnsNullWhenSkillMissing() {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend");
        when(trainingRepository.findById(1)).thenReturn(Optional.of(program));
        when(skillRepository.findById(9)).thenReturn(Optional.empty());

        TrainingProgram updated = trainingService.addSkillToProgram(1, 9);

        assertNull(updated);
        verify(trainingRepository, never()).save(any(TrainingProgram.class));
    }

    @Test
    void addSkillToProgramAddsSkillAndSaves() {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend");
        Skill skill = new Skill();
        skill.setId(4);
        skill.setName("Spring");
        when(trainingRepository.findById(1)).thenReturn(Optional.of(program));
        when(skillRepository.findById(4)).thenReturn(Optional.of(skill));
        when(trainingRepository.save(any(TrainingProgram.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TrainingProgram updated = trainingService.addSkillToProgram(1, 4);

        assertEquals(1, updated.getSkills().size());
        assertEquals("spring", updated.getSkills().getFirst().getName());
    }

    @Test
    void removeSkillFromProgramRemovesMatchingSkill() {
        TrainingProgram program = new TrainingProgram("Bootcamp", "Backend");
        Skill skill = new Skill();
        skill.setId(4);
        skill.setName("Spring");
        program.addSkill(skill);
        when(trainingRepository.findById(1)).thenReturn(Optional.of(program));
        when(trainingRepository.save(any(TrainingProgram.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TrainingProgram updated = trainingService.removeSkillFromProgram(1, 4);

        assertEquals(0, updated.getSkills().size());
    }
}
