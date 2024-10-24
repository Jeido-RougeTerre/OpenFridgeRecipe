package com.jeido.openfridgerecipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="recipes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recipe_id")
    private UUID id;

    private String name;

    private int CutleryNb;
    private long CaloricNb;

//    private List<String> ingredientsList;
//    private List<String> dieteticAlignment;
}
