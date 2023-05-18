package io.makeat.makeat_be.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3UploadService {


    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.bucket}")
    private String bucketName;


    public ResponseEntity<Object> uploadImage(MultipartFile image) {
        String fileName = image.getOriginalFilename();

        // S3 클라이언트 객체 생성
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://s3." + region + ".amazonaws.com", region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        // S3 버킷에 파일 업로드
        try {
            // 파일명 중복 방지를 위해 UUID를 추가
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

            // Content-Type 설정을 위한 ObjectMetadata 객체 생성
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");

            PutObjectResult result = s3Client.putObject(new PutObjectRequest(bucketName, uniqueFileName, image.getInputStream(), metadata));

            // 업로드한 파일의 S3 버킷 URL 반환
            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + uniqueFileName;
            return ResponseEntity.ok(fileUrl);

        } catch (AmazonServiceException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (SdkClientException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
