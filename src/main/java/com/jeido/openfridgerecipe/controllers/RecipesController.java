package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.dto.RecipesDtoReceive;
import com.jeido.openfridgerecipe.dto.RecipesDtoSend;
import com.jeido.openfridgerecipe.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipe")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    @GetMapping
    public ResponseEntity<List<RecipesDtoSend>> getAllRecipes (){
        return ResponseEntity.ok(recipesService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipesDtoSend> getRecipeById (@PathVariable("id")UUID id){
        return ResponseEntity.ok(recipesService.findById(id));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<RecipesDtoSend>> getRecipeByTag(@PathVariable("tag") String tagName){

        return ResponseEntity.ok(recipesService.findByTagName(tagName));
    }

    @GetMapping("/ingredient/{ingredientCode}")
    public ResponseEntity<List<RecipesDtoSend>> getRecipeByIngredientCode(@PathVariable("ingredientCode") String code){

        return ResponseEntity.ok(recipesService.getByIngredientCode(code));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<RecipesDtoSend>> getRecipeByName(@PathVariable("name") String name){
        return ResponseEntity.ok(recipesService.findByName(name));
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
