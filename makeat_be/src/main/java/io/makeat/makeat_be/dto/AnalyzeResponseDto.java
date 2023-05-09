package io.makeat.makeat_be.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class AnalyzeResponseDto {
    private String imgUrl;
    private List<FoodDto> foodList;
}

