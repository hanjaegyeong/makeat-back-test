package io.makeat.makeat_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

@RestController
public class FlaskToFlutterController {

    @Autowired
    private WebClient webClient;

    /**
     * 플라스크에서 받은 값 플러터로 전송하는 함수
     * @param abc
     * @return
     */
    @PostMapping(value = "/sendToFlutter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendToFlutter(@RequestBody int abc) {
        // 플러터로 값을 전송하는 코드
        String flutterUrl = "http://<flutter_ip>:<flutter_port>/receiveFromSpring";

        // WebClient를 사용하여 플러터로 값을 전송하고 응답을 받습니다.
        ResponseEntity<String> response = webClient.post()
                .uri(flutterUrl)
                .body(BodyInserters.fromValue(abc))
                .exchange()
                .flatMap(clientResponse -> clientResponse.toEntity(String.class))
                .block();

        // 응답을 그대로 반환합니다.
        return response;
    }
}