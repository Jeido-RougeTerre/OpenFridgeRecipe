package com.jeido.openfridgerecipe.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


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

    @ManyToMany
    @JoinTable(
            name = "ingredient_tags",
            joinColumns = @JoinColumn(name="ingredient_code"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

}
