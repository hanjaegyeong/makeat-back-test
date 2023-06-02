package io.makeat.makeat_be.controller;

import io.makeat.makeat_be.dto.NutrientDto;
import io.makeat.makeat_be.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
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

        Object body = imageURL.getBody(); // ResponseEntity의 body 가져오기

        String responseString = (String) body;
        // 정규표현식을 사용하여 원하는 URL 추출
        String pattern = "(https?://\\S+\\.\\S+)";
        Pattern urlPattern = Pattern.compile(pattern);
        Matcher matcher = urlPattern.matcher(responseString);

        if (matcher.find()) {
            String imageUrl = matcher.group(1); // 추출한 이미지 URL
            log.info(imageUrl);
        } else {
            log.info("이미지 URL을 찾을 수 없습니다.");
        }

        // 플러터로 값을 전송하는 코드
        String flutterUrl = "http://3.35.9.94:5000/image";


        // body에 key-value형태로 전송
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("image", String.valueOf(imageURL));

        // WebClient를 사용하여 플러터로 url을 전송하고 응답 받기

        ResponseEntity<String> nutrient = webClient.post()
                .uri(flutterUrl)
                .body(BodyInserters.fromFormData(requestBody))
                .exchange()
                .flatMap(clientResponse -> clientResponse.toEntity(String.class))
                .block();

        // 응답을 그대로 반환합니다.
        return nutrient;
    }
}