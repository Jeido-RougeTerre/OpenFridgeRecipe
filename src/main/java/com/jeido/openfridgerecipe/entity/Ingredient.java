package com.jeido.openfridgerecipe.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@Table(name="ingredients")
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @Column(name = "ingredient_code")
    private String code;

    private String name;
    private String quantity;
    private double calories;
}
