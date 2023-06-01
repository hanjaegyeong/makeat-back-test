package io.makeat.makeat_be.controller;

import io.makeat.makeat_be.dto.NutrientDto;
import io.makeat.makeat_be.service.S3UploadService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FlaskToFlutterController {

    private final WebClient webClient;
    private final S3UploadService ss;

    /**
     * 플러터에서 image받아서 s3 url 플라스크로 보낸 뒤
     * 플라스크에서 받은 영양소 json값 플러터로 리턴하는 함수
     * @param image
     * @return nutrient json
     */
    @PostMapping(value = "/analized-nutrient-info")
    public ResponseEntity<String> sendToFlutter(@RequestParam("image") MultipartFile image) {

        ResponseEntity<Object> imageURL = ss.uploadImage(image);

        // 플러터로 값을 전송하는 코드
        String flutterUrl = "http://3.35.9.94:5000/image";

        // WebClient를 사용하여 플러터로 url을 전송하고 응답을 받습니다.
        ResponseEntity<String> nutrient = webClient.post()
                .uri(flutterUrl)
                .body(BodyInserters.fromValue(imageURL))
                .exchange()
                .flatMap(clientResponse -> clientResponse.toEntity(String.class))
                .block();

        // 응답을 그대로 반환합니다.
        return nutrient;
    }
}