package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.dto.RecipesDtoSend;
import com.jeido.openfridgerecipe.entity.*;
import com.jeido.openfridgerecipe.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fridges")
public class FridgeController {

    private final FridgeService fridgeService;

    @Autowired
    public FridgeController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }


    @GetMapping("/{id}/recipes")
    public ResponseEntity<List<RecipesDtoSend>> getRecipesByUserFridge(@PathVariable long id) {
        List<RecipesDtoSend> suggestedRecipes = fridgeService.getSuggestedRecipes(id);
        return ResponseEntity.ok(suggestedRecipes);
    }

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredientByFridge(@PathVariable long id) {
        List<Ingredient> ingredients = fridgeService.getIngredientsByFridge(id);
        return ResponseEntity.ok(ingredients);
    }

    @PostMapping("/{id}/ingredients/{code}")
    public ResponseEntity<Fridge> addIngredient(@PathVariable long id, @PathVariable String code) {
        return ResponseEntity.ok(fridgeService.addIngredientToFridge(id, code));
    }

    @DeleteMapping("/{id}/ingredients")
    public ResponseEntity<Fridge> removeAllIngredient(@PathVariable long id) {
        return ResponseEntity.ok(fridgeService.removeAllIngredients(id));
    }

    @DeleteMapping("/{id}/ingredients/{code}")
    public ResponseEntity<Fridge> removeIngredient(@PathVariable long id, @PathVariable String code) {
        return ResponseEntity.ok(fridgeService.removeIngredientToFridge(id, code));
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<List<Tag>> getTagByFridge(@PathVariable long id) {
        List<Tag> tags = fridgeService.getTagsByFridge(id);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fridge> getFridge(@PathVariable long id) {
        return ResponseEntity.ok(fridgeService.getFridge(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Fridge> getFridge(@PathVariable UUID userId) {
        Fridge fridge = fridgeService.getFridgeByUserId(userId);
        return ResponseEntity.ok(fridge);
    }


}


