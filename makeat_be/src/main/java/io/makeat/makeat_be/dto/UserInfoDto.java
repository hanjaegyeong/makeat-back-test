package io.makeat.makeat_be.dto;

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
}
