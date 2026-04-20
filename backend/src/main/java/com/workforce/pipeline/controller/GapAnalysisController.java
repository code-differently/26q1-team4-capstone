package com.workforce.pipeline.controller;

import com.workforce.pipeline.dto.GapAnalysisDTO;
import com.workforce.pipeline.service.GapAnalysisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gap-analysis")
@CrossOrigin
public class GapAnalysisController {

    private final GapAnalysisService gapAnalysisService;

    public GapAnalysisController(GapAnalysisService gapAnalysisService) {
        this.gapAnalysisService = gapAnalysisService;
    }

    @GetMapping
    public List<GapAnalysisDTO> getGapAnalysis() {
        return gapAnalysisService.computeGapAnalysis();
    }
}