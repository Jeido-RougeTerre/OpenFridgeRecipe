package com.jeido.openfridgerecipe.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    private String code;
    private String name;
    private String quantity;
    private double calories;

}
