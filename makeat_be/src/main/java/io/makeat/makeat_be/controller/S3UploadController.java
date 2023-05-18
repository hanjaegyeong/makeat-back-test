package io.makeat.makeat_be.controller;

import io.makeat.makeat_be.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class S3UploadController {

    private final S3UploadService ss;

    /**
     * 플러터로부터 이미지 파일 body로 받아서 S3에 업로드하고 버켓 링크 반환하는 함수
     *
     * @param image
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFileToS3(@RequestParam("file") MultipartFile image) {

        ResponseEntity<Object> response = ss.uploadImage(image);
        return response;
    }
}
