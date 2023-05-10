package io.makeat.makeat_be.controller;

import io.makeat.makeat_be.service.NutritionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.parser.ParseException;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NutritionController {

    private final NutritionService ns;

    @GetMapping("/api/{foodName}/{quentity}/{quentityType}")
    public String getNutritionInfo(@PathVariable("foodName") String foodName, @PathVariable("quentity") Float quentity, @PathVariable("quentityType") String quentityType) throws IOException, ParseException {

        String nutrientInfo = ns.getNutrient(foodName, quentity, quentityType);

        return nutrientInfo;

    }
}