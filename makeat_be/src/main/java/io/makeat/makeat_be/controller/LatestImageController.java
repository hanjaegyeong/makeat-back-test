package io.makeat.makeat_be.controller;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LatestImageController {

    @Autowired
    private S3Client s3Client;

    /**
     * 플라스크로 S3 가장 최근 저장된 이미지 URL 반환하는 컨트롤러
     * @return
     */
    @GetMapping("/latest-image")
    public ResponseEntity<String> getLatestImageS3Url() {
        try {
            // S3 버킷에서 객체 리스트를 가져옵니다.
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket("makeatbucket")
                    .build();
            ListObjectsV2Response response = s3Client.listObjectsV2(request);
            List<S3Object> objects = response.contents();

            // 객체 리스트를 수정일자 기준으로 정렬합니다.
            List<S3Object> sortedObjects = objects.stream()
                    .sorted(Comparator.comparing(S3Object::lastModified).reversed())
                    .collect(Collectors.toList());

            // 가장 최근에 저장된 객체의 S3 링크를 생성합니다.
            String latestObjectKey = sortedObjects.get(0).key();
            String s3Url = generateS3Url("makeatbucket", latestObjectKey);

            // 플라스크에게 링크를 반환합니다.
            return ResponseEntity.ok(s3Url);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String generateS3Url(String bucketName, String key) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
}
