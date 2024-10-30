package com.jeido.openfridgerecipe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID userid;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "fridge_contents",
            joinColumns = @JoinColumn(name = "fridge_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_code")
    )
    private List<Ingredient> contenu = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        if (!contenu.contains(ingredient)) {
            contenu.add(ingredient);
        }
    }

}
