package com.jeido.openfridgerecipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @NotBlank(message = "This field can't be empty !")
    @Size(min=3, max=15, message = "Name should be between 3 and 15 characters long !")
    private String name;

    @NotBlank(message = "This field can't be empty !")
    @Size(min=3, max=20, message = "Surname should be between 3 and 20 characters long !")
    private String surname;

    @Email(message = "Email invalid !")
    private String email;

    @ManyToMany
    @JoinTable
            (name = "user_restrictions",
                    joinColumns = @JoinColumn(name = "user_id"))
    private List<Tags> ingredientsDietetique;

    @ManyToMany
    @JoinTable(
            name = "user_favorite_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private List<Recipes> recettesFav = new ArrayList<>();

    @Column(columnDefinition = "boolean default false")
    private boolean isAdmin;

    @NotBlank(message = "Password can't be empty!")
    private String password;

    public void addFavoriteRecipe(Recipes recipe) {
        if (!this.recettesFav.contains(recipe)) {
            this.recettesFav.add(recipe);
        }
    }

    public void addDieteticAlignement(Tags tag) {
        if (!this.ingredientsDietetique.contains(tag)) {
            this.ingredientsDietetique.add(tag);
        }
    }
}
