package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.entity.*;
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
    public ResponseEntity<List<Recipes>> getRecipesByUserFridge(@PathVariable UUID userId) {
        List<Recipes> suggestedRecipes = fridgeService.getSuggestedRecipes(userId);
        return ResponseEntity.ok(suggestedRecipes);
    }

    @GetMapping("/{userId}/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredientByFridge(@PathVariable UUID userId) {
        List<Ingredient> ingredients = fridgeService.getIngredientsByFridge(userId);
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{userId}/tags")
    public ResponseEntity<List<Tag>> getTagByFridge(@PathVariable UUID userId) {
        List<Tag> tags = fridgeService.getTagsByFridge(userId);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Fridge> getFridge(@PathVariable UUID userId) {
        Fridge fridge = fridgeService.getFridgeByUserId(userId);
        return ResponseEntity.ok(fridge);
    }

}


