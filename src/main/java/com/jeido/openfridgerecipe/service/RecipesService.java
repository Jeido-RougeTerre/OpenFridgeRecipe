package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.dto.RecipesDtoReceive;
import com.jeido.openfridgerecipe.dto.RecipesDtoSend;
import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.entity.Tags;
import com.jeido.openfridgerecipe.repository.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RecipesService {

    @Autowired
    private RecipesRepository recipesRepository;

    public Recipes getById (UUID id){
        return recipesRepository.findById(id).orElseThrow();
    }

    public List<Recipes> getByTags (Tags tags) {
        return recipesRepository.findByDieteticAlignment(tags);
    }

    public Recipes create (RecipesDtoReceive recipesDtoReceive){
        Recipes recipeCreated = Recipes.builder()
                .name(recipesDtoReceive.getName())
                .CutleryNb(recipesDtoReceive.getCutleryNb())
                .CaloricNb(recipesDtoReceive.getCaloricNb())
                //.ingredientsList(recipesDtoReceive.getIngredientsList())
                .dieteticAlignment(recipesDtoReceive.getDieteticAlignment())
                .build();

        return recipesRepository.save(recipeCreated);
    }

    public List<Recipes> getALl (){
        return (List<Recipes>) recipesRepository.findAll();
    }

    public RecipesDtoSend update(UUID id, RecipesDtoReceive received) {
        Recipes recipe = getById(id);
        recipe.setName(received.getName());
        recipe.setCutleryNb(received.getCutleryNb());
        recipe.setCaloricNb(received.getCaloricNb());
        recipe.setDieteticAlignment(received.getDieteticAlignment());

        recipesRepository.save(recipe);
        return recipeToRecipeDtoSend(recipe);
    }

    public boolean delete(UUID id) {
        Recipes recipe = getById(id);
        recipesRepository.delete(recipe);
        return true;
    }

    public RecipesDtoSend recipeToRecipeDtoSend (Recipes recipes){
        return RecipesDtoSend.builder()
                .id(recipes.getId())
                .name(recipes.getName())
                .CutleryNb(recipes.getCutleryNb())
                .CaloricNb(recipes.getCaloricNb())
                .dieteticAlignment(recipes.getDieteticAlignment())
                .build();
    }

}
