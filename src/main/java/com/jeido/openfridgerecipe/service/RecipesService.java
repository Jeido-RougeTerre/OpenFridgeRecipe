package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.dto.RecipesDtoReceive;
import com.jeido.openfridgerecipe.dto.RecipesDtoSend;
import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.entity.Tag;
import com.jeido.openfridgerecipe.exception.NotFoundException;
import com.jeido.openfridgerecipe.repository.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecipesService implements BaseService<RecipesDtoReceive, RecipesDtoSend> {


    private final RecipesRepository recipesRepository;

    private final IngredientService ingredientService;

    private final TagService tagService;

    @Autowired
    public RecipesService(RecipesRepository recipesRepository, IngredientService ingredientService, TagService tagService) {
        this.recipesRepository = recipesRepository;
        this.ingredientService = ingredientService;
        this.tagService = tagService;
    }

    @Override
    public RecipesDtoSend findById(UUID id) {
        return recipeToRecipeDtoSend(recipesRepository.findById(id).orElseThrow(()->new NotFoundException("Recipe not found at id :"+id)));
    }


    public List<RecipesDtoSend> findByTagName(String tagName) {
        Tag tag = tagService.findByName(tagName);
        return recipesToRecipeDtoSends(recipesRepository.findByDieteticAlignmentContains(tag));
    }

    public List<RecipesDtoSend> getByIngredientCode (String ingredientCode) {

        Ingredient ing = ingredientService.getIngredientByCode(ingredientCode);

        return recipesToRecipeDtoSends(recipesRepository.findByIngredientsContaining(ing));
    }

    private double computeCalories(Recipes recipes) {
        List<Ingredient> ingredients = recipes.getIngredients();
        double calories = 0.0;
        for (Ingredient ingredient : ingredients) {
            calories += ingredient.getCalories();
        }
        return calories;
    }

    @Override
    public RecipesDtoSend create (RecipesDtoReceive recipesDtoReceive){
        List<Ingredient> ingredients = new ArrayList<>();
        for (String code : recipesDtoReceive.getIngredientsCode()) {
            System.out.println("adding" + code);
            ingredients.add(ingredientService.getIngredientByCode(code));

        }

        System.out.println("CREATED");
        System.out.println(recipesDtoReceive.getCutleryNb());
        System.out.println(recipesDtoReceive.getIngredientsCode().get(0));

        Recipes recipeCreated = Recipes.builder()
                .name(recipesDtoReceive.getName())
                .cutleryNb(recipesDtoReceive.getCutleryNb())
                .ingredients(ingredients)
                .build();
        recipeCreated.setDieteticAlignment(tagService.computeForRecipe(recipeCreated));
        recipeCreated.setCaloricNb(computeCalories(recipeCreated));

        recipesRepository.save(recipeCreated);
        return recipeToRecipeDtoSend(recipeCreated);
    }

    @Override
    public List<RecipesDtoSend> getAll() {
        return recipesToRecipeDtoSends((List<Recipes>) recipesRepository.findAll());
    }

    @Override
    public RecipesDtoSend update(UUID id, RecipesDtoReceive received) {
        Recipes recipe = recipesRepository.findById(id).orElseThrow(()->new NotFoundException("Recipe not found at id :"+id));
        recipe.setName(received.getName());
        recipe.setCutleryNb(received.getCutleryNb());
        recipe.setCaloricNb(computeCalories(recipe));
        recipe.setDieteticAlignment(tagService.computeForRecipe(recipe));

        recipesRepository.save(recipe);
        return recipeToRecipeDtoSend(recipe);
    }

    @Override
    public boolean delete(UUID id) {
        Recipes recipe = recipesRepository.findById(id).orElseThrow(()->new NotFoundException("Recipe not found at id :"+id));
        recipesRepository.delete(recipe);
        return true;
    }

    public RecipesDtoSend recipeToRecipeDtoSend (Recipes recipes){
        return RecipesDtoSend.builder()
                .id(recipes.getId())
                .name(recipes.getName())
                .cutleryNb(recipes.getCutleryNb())
                .caloricNb(recipes.getCaloricNb())
                .ingredients(recipes.getIngredients())
                .tags(recipes.getDieteticAlignment())
                .build();
    }

    private List<RecipesDtoSend> recipesToRecipeDtoSends (List<Recipes> recipes){
        return recipes.stream().map(this::recipeToRecipeDtoSend).collect(Collectors.toList());
    }

    public List<RecipesDtoSend> suggestRecipesByIngredients(List<Ingredient> ingredients) {
        return recipesToRecipeDtoSends(recipesRepository.findByIngredientsIn(Collections.singleton(ingredients)));
    }

    public List<RecipesDtoSend> findByName(String name) {

        return recipesToRecipeDtoSends(recipesRepository.findByNameLikeIgnoreCase(name));
    }

}
