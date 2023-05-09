package io.makeat.makeat_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.makeat.makeat_be.entity.User;

@Entity
@Getter
@Setter
public class DietRecord {

    @Id @GeneratedValue
    @Column(name = "record_id")
    private long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "total_id")
    private NutrientTotal nutrientTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutrient_id")
    private Nutrient nutrient;

    private String imgUrl;

    private String date;

    private String time;

    private String comment;
}
