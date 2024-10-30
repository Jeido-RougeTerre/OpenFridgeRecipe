package com.jeido.openfridgerecipe.dto;

import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipesDtoSend {
    private UUID id;
    private String name;
    private int cutleryNb;
    private double caloricNb;
    private List<Ingredient> ingredients;
    private List<Tag> tags;
}
