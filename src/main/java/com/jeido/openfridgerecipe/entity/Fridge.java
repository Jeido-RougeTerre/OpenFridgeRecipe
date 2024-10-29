package com.jeido.openfridgerecipe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinTable
    private User user;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "fridge_contents",
            joinColumns = @JoinColumn(name = "fridge_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_code")
    )
    private List<Ingredient> contenu = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "fridge_tags",
            joinColumns = @JoinColumn(name = "fridge_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    private int nbCouvert;


    public Fridge(User user, int nbrCouvert) {
        this.user = user;
        this.nbCouvert = nbrCouvert;
    }

    public void addIngredient(Ingredient ingredient) {
        if (!contenu.contains(ingredient)) {
            contenu.add(ingredient);
        }
    }

    public void removeIngredient(Ingredient ingredient) {
        contenu.remove(ingredient);
    }

}
