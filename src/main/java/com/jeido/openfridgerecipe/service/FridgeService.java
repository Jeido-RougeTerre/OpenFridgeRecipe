package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.entity.*;
import com.jeido.openfridgerecipe.repository.FridgeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;


@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final UserService userservice;


    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, UserService uservice) {
        this.fridgeRepository = fridgeRepository;
        this.userservice = uservice;

    }

    public Fridge getFridgeByUserId(UUID userId) {
        return fridgeRepository.findByUser(userservice.getUserById(userId)).orElseThrow(() -> new RuntimeException("Fridge not found for this User"));
    }

    public Fridge addIngredientToFridge(Long fridgeId, List<Ingredient> ingredients) {

        Fridge fridge = fridgeRepository.findById(fridgeId)
                .orElseThrow(() -> new RuntimeException("Fridge not found with id: " + fridgeId));
        ingredients.forEach(fridge::addIngredient);
        return (Fridge) fridgeRepository.save(fridge).getContenu();
    }

    public List<Ingredient> removeIngredientsFromFridge(Long fridgeId, List<Ingredient> ingredients) {
        Fridge fridge = fridgeRepository.findById(fridgeId)
                .orElseThrow(() -> new RuntimeException("Fridge not found with id: " + fridgeId));
        ingredients.forEach(fridge::removeIngredient);

        return fridgeRepository.save(fridge).getContenu();
    }

    public Fridge createFridge(User user, int nbrCouvert) {
        Fridge fridge = new Fridge(user, nbrCouvert);
        return fridgeRepository.save(fridge);
    }

    public List<Ingredient> getAllIngredient (Long fridgeId) {
        Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new RuntimeException("Fridge not found with id: " + fridgeId));
        return fridge.getContenu();
    }

    public List<Recipes> getSuggestedRecipes(UUID userId) {
        Fridge fridge = fridgeRepository.findByUser(userservice.getUserById(userId))
                .orElseThrow(() -> new RuntimeException("Fridge not found for user id: " + userId));

        List<Ingredient> ingredients = fridge.getContenu();

        return RecipesService.suggestRecipesByIngredients(ingredients);
    }

    public List<Ingredient> getIngredientsByFridge(UUID userId) {
        Fridge fridge = getFridgeByUserId(userId);
        return fridge.getContenu();
    }

    public List<Tag> getTagsByFridge(UUID userId) {
        Fridge fridge = getFridgeByUserId(userId);
        return fridge.getTags();
    }

    public List<Recipes> getSuggestedRecipesByIngredients(UUID userId) {
        Fridge fridge = fridgeRepository.findByUser(userservice.getUserById(userId))
                .orElseThrow(() -> new RuntimeException("Fridge not found for user id: " + userId));
        List<Ingredient> ingredients = fridge.getContenu();
        return RecipesService.suggestRecipesByIngredients(ingredients);
    }

    public List<Recipes> getSuggestedRecipesByTags(UUID userId) {
        Fridge fridge = fridgeRepository.findByUser(userservice.getUserById(userId))
                .orElseThrow(() -> new RuntimeException("Fridge not found for user id: " + userId));
        List<Tag> tags = fridge.getTags();
        return RecipesService.suggestRecipesByTag(tags);
    }
}
