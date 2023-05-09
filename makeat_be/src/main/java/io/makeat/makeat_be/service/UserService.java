package io.makeat.makeat_be.service;

import io.makeat.makeat_be.dto.UserInfoDto;
import io.makeat.makeat_be.entity.User;
import io.makeat.makeat_be.entity.UserInfo;
import io.makeat.makeat_be.repository.UserInfoRepository;
import io.makeat.makeat_be.repository.UserRepository;
import io.makeat.makeat_be.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.secret}")
    private String secret;

    private Long expireMs = 1000 * 60 * 60 * 24L; // 24시간

    private final UserInfoRepository userInfoRepository;

    private final UserRepository userRepository;

    public String createJwt(String userPk) {
        return JwtUtil.createJwt(userPk, secret, expireMs);
    }

    /**
     * 회원가입
     */
    public User login(String loginKind, String loginId) {
        // 등록된 회원인지 여부 확인
        // 등록된 회원이면 회원 정보 반환
        // 등록되지 않은 회원이면 회원 정보 저장 후 반환
        User user = userRepository.findUserByLoginKindAndLoginId(loginKind, loginId);

        if (user == null) {
            User newUser = new User(loginKind, loginId);

            return userRepository.save(newUser);
        }

        return user;
    }

    public void saveUserInfo(UserInfoDto userInfoDto, String userPk) {

        User user = userRepository.findById(userPk).get();
        UserInfo userInfo = new UserInfo(user, userInfoDto);

        userInfoRepository.save(userInfo);
    }

    public UserInfoDto getUserInfo(User user) {

        UserInfo userInfo = userInfoRepository.findUserInfoByUser(Optional.ofNullable(user));

        if(userInfo == null) {
            return null;
        }

        return new UserInfoDto(userInfo);
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
