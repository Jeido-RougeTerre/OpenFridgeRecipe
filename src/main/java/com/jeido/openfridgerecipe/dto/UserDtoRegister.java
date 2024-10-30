package com.jeido.openfridgerecipe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRegister {
    @NotBlank(message = "This field can't be empty !")
    @Size(min=3, max=15, message = "Name should be between 3 and 15 characters long !")
    private String name;
    @NotBlank(message = "This field can't be empty !")
    @Size(min=3, max=20, message = "Surname should be between 3 and 20 characters long !")
    private String surname;
    @Email(message = "Email invalid !")
    private String email;
    @NotBlank(message = "Password can't be empty!")
    @Size(min=8, max=20, message = "Password should be between 8 and 20 characters long !")
    private String password;

}
