package io.makeat.makeat_be.service;

import io.makeat.makeat_be.dto.UserInfoDto;
import io.makeat.makeat_be.entity.User;
import io.makeat.makeat_be.entity.UserInfo;
import io.makeat.makeat_be.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserInfoRepository userInfoRepository;

    public void saveUserInfo(UserInfoDto userInfoDto) {

        UserInfo userInfo = new UserInfo();
        User user = new User();
        userInfo.setUser(user);
        userInfo.setAge(userInfoDto.getAge());
        userInfo.setGender(userInfoDto.getGender());
        userInfo.setHeight(userInfoDto.getHeight());
        userInfo.setWeight(userInfoDto.getWeight());
        userInfo.setBmi(userInfoDto.getBmi());

        userInfoRepository.save(userInfo);
    }
}
