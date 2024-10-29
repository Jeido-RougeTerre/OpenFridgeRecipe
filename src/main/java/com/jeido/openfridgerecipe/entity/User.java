package com.jeido.openfridgerecipe.entity;

import jakarta.persistence.*;

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

    private String name;

    private String surname;

    private String email;

    @ManyToMany
    @JoinTable
            (name = "user_restrictions",
                    joinColumns = @JoinColumn(name = "user_id"))
    private List<Tag> ingredientsDietetique;

    @ManyToMany
    @JoinTable(
            name = "user_favorite_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private List<Recipes> favoriteRecipe = new ArrayList<>();

    @Column(columnDefinition = "boolean default false")
    private boolean isAdmin;

   private String password;
}
