package com.jeido.openfridgerecipe.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ingredients")

public class Ingredient {

    @Id
    private Long id;
    @ManyToMany(mappedBy = "ingredientsDietetique")
    private List<User> users;


}
