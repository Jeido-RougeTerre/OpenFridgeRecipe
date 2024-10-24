package com.jeido.openfridgerecipe.dto;

import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipesDtoSend {
    private UUID id;
    private String name;
    private int CutleryNb;
    private long CaloricNb;
//    private List<String> ingredientsList;
//    private List<String> dieteticAlignment;
}
