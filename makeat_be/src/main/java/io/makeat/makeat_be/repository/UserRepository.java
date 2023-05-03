package io.makeat.makeat_be.repository;


import io.makeat.makeat_be.entity.User;
import io.makeat.makeat_be.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByLoginKindAndLoginId(String loginKind, String loginId);

}
