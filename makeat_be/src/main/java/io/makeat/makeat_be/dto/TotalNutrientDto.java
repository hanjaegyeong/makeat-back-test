package io.makeat.makeat_be.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TotalNutrientDto {
    private String date;
    private NutrientDto totalNutrient;
}
