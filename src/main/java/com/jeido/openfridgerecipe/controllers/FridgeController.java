package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.entity.Tags;
import com.jeido.openfridgerecipe.entity.User;
import com.jeido.openfridgerecipe.service.FridgeService;
import com.jeido.openfridgerecipe.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fridges")
public class FridgeController {

    private final FridgeService fridgeService;
    private final IngredientService ingredientService;

    @Autowired
    public FridgeController(FridgeService fridgeService, IngredientService ingredientService) {
        this.fridgeService = fridgeService;
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{userId}/recipes")
    public ResponseEntity<List<Recipes>> getRecipesByUserFridge(@PathVariable Long userId) {
        List<Recipes> suggestedRecipes = fridgeService.getSuggestedRecipes(userId);
        return ResponseEntity.ok(suggestedRecipes);
    }
}
