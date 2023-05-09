package io.makeat.makeat_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FoodImg {
    @Id @GeneratedValue
    @Column(name = "food_img_id")
    private long foodImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private DietRecord dietRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    private float capacity;
}
