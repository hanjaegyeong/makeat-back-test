package io.makeat.makeat_be.entity;

import io.makeat.makeat_be.dto.UserInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserInfo {
    @Id
    @GeneratedValue
    @Column(name = "info_id")
    private long infoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int age;

    private String gender; // M, W

    private float height;

    private float weight;

    private float bmi;

    public UserInfo() {
    }

    public UserInfo(User user, UserInfoDto userInfoDto) {
        this.user = user;
        this.age = userInfoDto.getAge();
        this.gender = userInfoDto.getGender();
        this.height = userInfoDto.getHeight();
        this.weight = userInfoDto.getWeight();
        this.bmi = userInfoDto.getBmi();
    }
}