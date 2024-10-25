package com.jeido.openfridgerecipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipesDtoReceive {
    private UUID id;
    private String name;
    private int CutleryNb;
    private long CaloricNb;
//    private List<String> ingredientsList;
//    private List<String> dieteticAlignment;
}
