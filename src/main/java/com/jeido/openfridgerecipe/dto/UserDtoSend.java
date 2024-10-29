package com.jeido.openfridgerecipe.dto;

import com.jeido.openfridgerecipe.entity.Recipes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoSend {
    private UUID id;
    private String name;
    private String surname;
    private List<Recipes> favoriteRecipes;
    private String email;
    private Long fridgeId;
    private boolean isAdmin;
}
