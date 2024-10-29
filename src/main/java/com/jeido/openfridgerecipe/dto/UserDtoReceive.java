package com.jeido.openfridgerecipe.dto;

import com.jeido.openfridgerecipe.entity.Recipes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoReceive {

    @Email(message = "Email invalid !")
    private String email;

    @NotBlank(message = "Password can't be empty!")
    @Size(min=8, max=20, message = "Password should be between 8 and 20 characters long !")
    private String password;


    private String name;
    private String surname;
    private List<Recipes> favoriteRecipes;
}
