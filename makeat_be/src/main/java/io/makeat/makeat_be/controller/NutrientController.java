package io.makeat.makeat_be.controller;

import io.makeat.makeat_be.dto.FoodDto;
import io.makeat.makeat_be.service.NutrientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.parser.ParseException;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NutrientController {

    private final NutrientService ns;

    /**
     * 플러터로부터 음식 명, 음식 양 받아서 OpenAPI 통해 영양정보 반환
     * @param food
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @GetMapping("/nutrientInfo")
    public String getNutrientInfo(@ModelAttribute FoodDto food) throws IOException, ParseException {

        String nutrientInfo = ns.getNutrient(food.getFoodName(), food.getQuentity(), food.getQuentityType());

        return nutrientInfo;

    }


}