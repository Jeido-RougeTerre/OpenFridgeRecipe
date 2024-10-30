package com.jeido.openfridgerecipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipesDtoReceive {
    @NotBlank(message = "This field can't be empty !")
    @Size(min=1, max=100, message = "Recipe Name should not be empty !")
    private String name;
    private int cutleryNb = 1;
    private List<String> ingredientsCode;
}
