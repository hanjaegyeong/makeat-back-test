package io.makeat.makeat_be.dto;

import io.makeat.makeat_be.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private int age;

    private String gender;

    private float height;

    private float weight;

    private float bmi;

    public UserInfoDto() {
    }

    public UserInfoDto(UserInfo userInfo) {
        this.age = userInfo.getAge();
        this.gender = userInfo.getGender();
        this.height = userInfo.getHeight();
        this.weight = userInfo.getWeight();
        this.bmi = userInfo.getBmi();
    }
}
