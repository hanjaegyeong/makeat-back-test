package io.makeat.makeat_be.repository;

import io.makeat.makeat_be.entity.User;
import io.makeat.makeat_be.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findUserInfoByUser(Optional<User> user);

}
