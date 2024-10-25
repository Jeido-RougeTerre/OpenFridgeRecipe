package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.dto.RecipesDtoReceive;
import com.jeido.openfridgerecipe.dto.RecipesDtoSend;
import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.repository.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecipesService {

    @Autowired
    private RecipesRepository recipesRepository;

    public Recipes getById (UUID id){
        return recipesRepository.findById(id).orElseThrow();
    }

    public Recipes create (RecipesDtoReceive recipesDtoReceive){
        Recipes recipeCreated = Recipes.builder()
                .name(recipesDtoReceive.getName())
                .CutleryNb(recipesDtoReceive.getCutleryNb())
                .CaloricNb(recipesDtoReceive.getCaloricNb())
                //.ingredientsList(recipesDtoReceive.getIngredientsList())
                //.dieteticAlignment(recipesDtoReceive.getDieteticAlignment())
                .build();

        return recipesRepository.save(recipeCreated);
    }

    public List<Recipes> getALl (){
        return (List<Recipes>) recipesRepository.findAll();
    }

    public Recipes update(Recipes recipe) {
        return recipesRepository.save(recipe);
    }

    public void delete(Recipes recipe) {
        recipesRepository.delete(recipe);
    }

}
