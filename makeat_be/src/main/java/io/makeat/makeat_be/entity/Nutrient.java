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
public class Nutrient {
    @Id
    @GeneratedValue
    @Column(name = "nutrient_id")
    private Long nutrientId;

    private float carbo;
    private float protein;
    private float fat;
    private float na;
    private float kcal;
}
