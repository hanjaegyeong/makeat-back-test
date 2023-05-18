package io.makeat.makeat_be.controller;

import io.makeat.makeat_be.dto.NutrientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

@RestController
public class FlaskToFlutterController {

    @Autowired
    private WebClient webClient;

    /**
     * 플러터에서 요청받으면 플라스크에서 받은 영양소 json값 플러터로 전송하는 함수
     * @param image
     * @return nutrient json
     */
    @PostMapping(value = "/sendToFlutter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendToFlutter(@RequestParam("file") MultipartFile image) {
        // 플러터로 값을 전송하는 코드
        String flutterUrl = "http://<flutter_ip>:5000/image ";

        // WebClient를 사용하여 플러터로 값을 전송하고 응답을 받습니다.
        ResponseEntity<String> nutrient = webClient.post()
                .uri(flutterUrl)
                .body(BodyInserters.fromValue(image))
                .exchange()
                .flatMap(clientResponse -> clientResponse.toEntity(String.class))
                .block();

        // 응답을 그대로 반환합니다.
        return nutrient;
    }
}