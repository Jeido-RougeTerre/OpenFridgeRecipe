package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.dto.RecipesDtoReceive;
import com.jeido.openfridgerecipe.dto.RecipesDtoSend;
import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.service.RecipesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipe")
public class RecipesController {

    private RecipesService recipesService;
    public RecipesController (RecipesService recipesService){
        this.recipesService = recipesService;
    }

    @GetMapping
    public ResponseEntity<List<RecipesDtoSend>> getRecipes (){
        List<Recipes> recipesList = recipesService.getALl();
        List<RecipesDtoSend> recipesDtoSends = new ArrayList<>();
        for (Recipes recipe : recipesList){
            recipesDtoSends.add(new RecipesDtoSend(recipe.getId(),recipe.getName(),recipe.getCutleryNb(),recipe.getCaloricNb()));//,recipe.getIngredientsList(),recipe.getDieteticAlignment()));
        }

        return ResponseEntity.ok(recipesDtoSends);
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipesDtoSend> getRecipeById(@PathVariable("id") UUID id){
        Recipes recipe = recipesService.getById(id);
        RecipesDtoSend recipesDtoSend = new RecipesDtoSend(recipe.getId(),recipe.getName(),recipe.getCutleryNb(),recipe.getCaloricNb());//,recipe.getIngredientsList(),recipe.getDieteticAlignment());
        return ResponseEntity.ok(recipesDtoSend);
    }

    @PostMapping
    public ResponseEntity<RecipesDtoSend> createRecipe (@Validated @RequestBody RecipesDtoReceive recipesDtoReceive){
        Recipes recipe = recipesService.create(recipesDtoReceive);
        RecipesDtoSend recipesDtoSend = new RecipesDtoSend(recipe.getId(),recipe.getName(),recipe.getCutleryNb(),recipe.getCaloricNb());//,recipe.getIngredientsList(),recipe.getDieteticAlignment());
        return new ResponseEntity<>(recipesDtoSend,HttpStatus.CREATED);
    }
}
