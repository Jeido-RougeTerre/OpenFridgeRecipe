package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredient/")
public class IngredientAPIController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientAPIController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("{code}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("code") String code) {
        Ingredient ing = ingredientService.getIngredientByCode(code);


        return ResponseEntity.ok(ing);
    }

}
