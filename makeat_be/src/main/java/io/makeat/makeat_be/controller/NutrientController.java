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

    @GetMapping("/nutrientInfo")
    public String getNutrientInfo(@ModelAttribute FoodDto food) throws IOException, ParseException {

        String nutrientInfo = ns.getNutrient(food.getFoodName(), food.getQuentity(), food.getQuentityType());

        return nutrientInfo;

    }
}