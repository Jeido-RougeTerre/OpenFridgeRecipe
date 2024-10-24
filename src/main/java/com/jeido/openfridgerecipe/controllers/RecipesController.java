package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.dto.RecipesDtoReceive;
import com.jeido.openfridgerecipe.dto.RecipesDtoSend;
import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.entity.Tags;
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
    public ResponseEntity<List<RecipesDtoSend>> getAllFilm (){
        return ResponseEntity.ok(recipesService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipesDtoSend> getRecipeById(@PathVariable("id") UUID id){
        Recipes recipe = recipesService.getById(id);
        RecipesDtoSend recipesDtoSend = new RecipesDtoSend(recipe.getId(),recipe.getName(),recipe.getCutleryNb(),recipe.getCaloricNb(),recipe.getDieteticAlignment());//,recipe.getIngredientsList(),recipe.getDieteticAlignment());
        return ResponseEntity.ok(recipesDtoSend);
    }

    @GetMapping("{tags}")
    public ResponseEntity<List<RecipesDtoSend>> getRecipeByTag(@PathVariable("tags") Tags tag){
        List<Recipes> recipesList = recipesService.getByTags(tag);
        List<RecipesDtoSend> recipesDtoSends = new ArrayList<>();
        for (Recipes recipe : recipesList){
            recipesDtoSends.add(new RecipesDtoSend(recipe.getId(),recipe.getName(),recipe.getCutleryNb(),recipe.getCaloricNb(),recipe.getDieteticAlignment()));//,recipe.getIngredientsList(),recipe.getDieteticAlignment()));
        }

        return ResponseEntity.ok(recipesDtoSends);
    }

    @PostMapping
    public ResponseEntity<RecipesDtoSend> createRecipe (@Validated @RequestBody RecipesDtoReceive recipesDtoReceive){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipesService.create(recipesDtoReceive));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipesDtoSend> updateRecipe (@PathVariable("id") UUID id,@RequestBody RecipesDtoReceive recipesDtoReceive) {
        return ResponseEntity.ok(recipesService.update(id, recipesDtoReceive));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe (@PathVariable("id")UUID id){
        recipesService.delete(id);
        return ResponseEntity.ok("Recipe with id :" +id+" is deleted");
    }

}
