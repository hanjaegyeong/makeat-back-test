package io.makeat.makeat_be.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RecordDto {
    private NutrientDto nutrientDto;

    private AnalyzeResponseDto analyzedData;

    private String date;

    private String time;

    private String comment;

}
