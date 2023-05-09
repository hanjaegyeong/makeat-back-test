package io.makeat.makeat_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private long userId;

    @Column(name = "login_kind")
    private String loginKind;

    @Column(name = "login_id")
    private String loginId;   // 해당 간편로그인시 인증서버에서 가져올 id

    public User() {
    }

    public User(String loginKind, String loginId) {
        this.loginKind = loginKind;
        this.loginId = loginId;
    }
}
