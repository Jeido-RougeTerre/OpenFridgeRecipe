package com.jeido.openfridgerecipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipesDtoReceive {
    private String name;
    private int cutleryNb;
    private List<String> ingredientsCode;
}
