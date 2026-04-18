package com.workforce.pipeline.mapper;

import com.workforce.pipeline.dto.GapAnalysisDTO;

public class GapAnalysisMapper {

    public static GapAnalysisDTO build(String region,
                                       String industry,
                                       double roleGapScore,
                                       double skillGapScore) {

        return new GapAnalysisDTO(
                region,
                industry,
                roleGapScore,
                skillGapScore
        );
    }
}