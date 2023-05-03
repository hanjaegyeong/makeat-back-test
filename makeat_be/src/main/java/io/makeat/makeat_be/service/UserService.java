package io.makeat.makeat_be.service;

import io.makeat.makeat_be.dto.UserInfoDto;
import io.makeat.makeat_be.entity.User;
import io.makeat.makeat_be.entity.UserInfo;
import io.makeat.makeat_be.repository.UserInfoRepository;
import io.makeat.makeat_be.repository.UserRepository;
import io.makeat.makeat_be.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secret;

    private Long expireMs = 1000 * 60 * 60 * 24L; // 24시간

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserRepository userRepository;

    public String createJwt(String userPk) {
        return JwtUtil.createJwt(userPk, secret, expireMs);
    }

    /**
     * 회원가입
     */
    public Optional<User> login(String loginKind, String loginId) {
        User user = new User();
        user.setLoginKind(loginKind);
        user.setLoginId(loginId);

        // 등록된 회원인지 여부 확인
        // 등록된 회원이면 회원 정보 반환
        // 등록되지 않은 회원이면 회원 정보 저장 후 반환
        Optional<User> userOptional = userRepository.findUserByLoginKindAndLoginId(loginKind, loginId);

        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            return Optional.of(userRepository.save(user));
        }
    }

    public void saveUserInfo(UserInfoDto userInfoDto, String userPk) {

        UserInfo userInfo = new UserInfo();
        User user = userRepository.findById(userPk).get();
        userInfo.setUser(user);
        userInfo.setAge(userInfoDto.getAge());
        userInfo.setGender(userInfoDto.getGender());
        userInfo.setHeight(userInfoDto.getHeight());
        userInfo.setWeight(userInfoDto.getWeight());
        userInfo.setBmi(userInfoDto.getBmi());

        userInfoRepository.save(userInfo);
    }

    public UserInfoDto getUserInfo(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        UserInfo userInfo = userInfoRepository.findUserInfoByUser(Optional.ofNullable(user));

        if(userInfo == null) {
            return null;
        }

        userInfoDto.setAge(userInfo.getAge());
        userInfoDto.setGender(userInfo.getGender());
        userInfoDto.setHeight(userInfo.getHeight());
        userInfoDto.setWeight(userInfo.getWeight());
        userInfoDto.setBmi(userInfo.getBmi());

        return userInfoDto;
    }

    public void modifyUserInfo(UserInfoDto userInfoDto, String userPk) {
        User user = userRepository.findById(userPk).get();
        UserInfo userInfo = userInfoRepository.findUserInfoByUser(Optional.ofNullable(user));

        userInfo.setAge(userInfoDto.getAge());
        userInfo.setGender(userInfoDto.getGender());
        userInfo.setHeight(userInfoDto.getHeight());
        userInfo.setWeight(userInfoDto.getWeight());
        userInfo.setBmi(userInfoDto.getBmi());

        userInfoRepository.save(userInfo);
    }

    public void deleteUser(String userPk) {
        User user = userRepository.findById(userPk).get();
        UserInfo userInfo = userInfoRepository.findUserInfoByUser(Optional.ofNullable(user));

        userInfoRepository.delete(userInfo);
        userRepository.delete(user);
    }
}
