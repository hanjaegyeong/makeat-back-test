package io.makeat.makeat_be.controller;

import io.makeat.makeat_be.dto.UserInfoDto;
import io.makeat.makeat_be.entity.User;
import io.makeat.makeat_be.entity.UserInfo;
import io.makeat.makeat_be.repository.UserInfoRepository;
import io.makeat.makeat_be.repository.UserRepository;
import io.makeat.makeat_be.service.KakaoLoginService;
import io.makeat.makeat_be.service.NaverLoginService;
import io.makeat.makeat_be.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final KakaoLoginService ks;
    private final NaverLoginService ns;
    private final UserService userService;


    @GetMapping("/kakao")
    public ResponseEntity getKakaoCI(@RequestParam String code) throws IOException{

        //인증코드로 토큰, 유저정보 GET
        String token = ks.getToken(code);
        Map<String, Object> userInfo = ks.getUserInfo(token);

        // user 확인 및 신규 유저 저장
        User user = userService.login("kakao", userInfo.get("id").toString());
        if (user==null) {
            return new ResponseEntity(null, null, HttpStatus.BAD_REQUEST);
        }

        // jwt 생성
        String accessJwt = userService.createJwt(String.valueOf(user.getUserId()));

        //헤더에 accessJwt 담기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessJwt);

        UserInfoDto userInfoDto = userService.getUserInfo(user);

        return new ResponseEntity(userInfoDto, headers, HttpStatus.OK);
    }

    @GetMapping("/naver")
    public ResponseEntity getNaverCI(@RequestParam String code) throws IOException, ParseException {

        //인증코드로 토큰, 유저정보 GET
        String token = ns.getToken(code);
        Map<String, Object> userInfo = ns.getUserInfo(token);

        // user 확인 및 신규 유저 저장
        User user = userService.login("naver", userInfo.get("id").toString());
        if (user==null) {
            return new ResponseEntity(null, null, HttpStatus.BAD_REQUEST);
        }

        // jwt 생성
        String accessJwt = userService.createJwt(String.valueOf(user.getUserId()));

        //헤더에 accessJwt 담기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessJwt);

        UserInfoDto userInfoDto = userService.getUserInfo(user);

        return new ResponseEntity(userInfoDto, headers, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            Authentication authentication
    ) {
        String userPk = (String) authentication.getCredentials();
        if (userPk == null) {
            // 검증되지 않은 사용자라면 404 에러 반환
            return new ResponseEntity(null, null, HttpStatus.BAD_REQUEST);
        }

        userService.deleteUser(userPk);
        return new ResponseEntity(null, null, HttpStatus.OK);
    }

    /**
     * 1. Http 헤더 중 Authorization 을 받아 사용자 검증
     * 2. RequestParameter로 받은 userInfo를 해당 사용자에 저장
     *
     * @param userInfoDto
     */
    @PostMapping("/info")
    public ResponseEntity saveUserInfo(
            Authentication authentication,
            @RequestBody UserInfoDto userInfoDto
    ) {
        String userPk = (String) authentication.getCredentials();

        if (userPk == null) {
            // 검증되지 않은 사용자라면 404 에러 반환
            return new ResponseEntity(null, null, HttpStatus.BAD_REQUEST);
        }

        // 사용자 정보 저장
        userService.saveUserInfo(userInfoDto, userPk);

        return new ResponseEntity(null, null, HttpStatus.OK);
    }

    @PutMapping("/info")
    public ResponseEntity modifyUserInfo(
            @RequestBody UserInfoDto userInfoDto,
            Authentication authentication
    ) {
        String userPk = (String) authentication.getCredentials();
        if (userPk == null) {
            // 검증되지 않은 사용자라면 404 에러 반환
            return new ResponseEntity(null, null, HttpStatus.BAD_REQUEST);
        }

        // 사용자 수정
        userService.modifyUserInfo(userInfoDto, userPk);

        return new ResponseEntity(null, null, HttpStatus.OK);
    }
}
