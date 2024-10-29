package com.jeido.openfridgerecipe.service;


import com.jeido.openfridgerecipe.entity.*;
import com.jeido.openfridgerecipe.exception.NotFoundException;
import com.jeido.openfridgerecipe.repository.FridgeRepository;
import com.jeido.openfridgerecipe.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final RecipesService recipesService;
    private final IngredientRepository ingredientRepository;
    private final TagService tagService;


    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, RecipesService recipesService, IngredientRepository ingredientRepository, TagService tagService) {
        this.fridgeRepository = fridgeRepository;

        this.recipesService = recipesService;
        this.ingredientRepository = ingredientRepository;
        this.tagService = tagService;
    }

    public Fridge getFridgeByUserId(UUID userId) {
        return fridgeRepository.findByUserid(userId).orElseThrow(() -> new NotFoundException("Fridge not found for this User"));
    }

    public Fridge addIngredientToFridge(Long fridgeId, String code) {

        Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new NotFoundException("Fridge not found with id #" + fridgeId));
        Ingredient ingredient = ingredientRepository.findById(code).orElseThrow(() -> new NotFoundException("Ingredient not found with code #" + code));
        fridge.addIngredient(ingredient);

        return fridgeRepository.save(fridge);
    }

    public Fridge removeIngredientToFridge(Long fridgeId, String code) {

        Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new  NotFoundException("Fridge not found with id #" + fridgeId));

        fridge.getContenu().removeIf(ingredient -> ingredient.getCode().equals(code));

        return fridgeRepository.save(fridge);
    }

    public Fridge createFridge(UUID userID) {
        Fridge fridge = Fridge.builder().userid(userID).build();
        return fridgeRepository.save(fridge);
    }

    public List<Recipes> getSuggestedRecipes(long id) {
        Fridge fridge = fridgeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fridge not found with id: " + id));

        List<Ingredient> ingredients = fridge.getContenu();

        return recipesService.suggestRecipesByIngredients(ingredients);
    }

    public List<Ingredient> getIngredientsByFridge(long id) {
        Fridge fridge = getFridge(id);
        return fridge.getContenu();
    }

    public List<Tag> getTagsByFridge(long id) {
        Fridge fridge = fridgeRepository.findById(id).orElseThrow(() -> new NotFoundException("Fridge not found with id: " + id));
        return tagService.computeFromIngredients(fridge.getContenu());
    }

    public Fridge getFridge(long id) {
        return fridgeRepository.findById(id).orElseThrow(() -> new NotFoundException("Fridge not found with id: " + id));
    }

    public Fridge removeAllIngredients(long id) {
        Fridge fridge = fridgeRepository.findById(id).orElseThrow(() -> new NotFoundException("Fridge not found with id: " + id));
        fridge.setContenu(new ArrayList<>());
        return fridgeRepository.save(fridge);
    }
}
