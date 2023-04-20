package io.makeat.makeat_be.entity;

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
    private Long infoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int age;

    private String gender; // M, W

    private float height;

    private float weight;

    private float bmi;
}