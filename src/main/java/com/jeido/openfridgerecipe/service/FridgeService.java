package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.entity.*;
import com.jeido.openfridgerecipe.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final UserService userService;
    private final RecipesService recipesService;


    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, UserService userService, RecipesService recipesService) {
        this.fridgeRepository = fridgeRepository;
        this.userService = userService;
        this.recipesService = recipesService;
    }

    public Fridge getFridgeByUserId(UUID userId) {
        return fridgeRepository.findByUser(userService.getUserById(userId)).orElseThrow(() -> new RuntimeException("Fridge not found for this User"));
    }

    public Fridge addIngredientToFridge(Long fridgeId, Ingredient ingredient) {

        Fridge fridge = fridgeRepository.findById(fridgeId).orElse(null);

        if (fridge == null) {
            throw new RuntimeException("Fridge not found");
        }

        fridge.addIngredient(ingredient);

        return fridgeRepository.save(fridge);
    }

    public Fridge removeIngredientToFridge(Long fridgeId, Ingredient ingredient) {

        Fridge fridge = fridgeRepository.findById(fridgeId).orElse(null);
        if (fridge == null) {
            throw new RuntimeException("Fridge not found");
        }

        fridge.removeIngredient(ingredient);

        return fridgeRepository.save(fridge);
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
        Fridge fridge = fridgeRepository.findByUser(userService.getUserById(userId))
                .orElseThrow(() -> new RuntimeException("Fridge not found for user id: " + userId));

        List<Ingredient> ingredients = fridge.getContenu();

        return recipesService.suggestRecipesByIngredients(ingredients);
    }

    public List<Ingredient> getIngredientsByFridge(UUID userId) {
        Fridge fridge = getFridgeByUserId(userId);
        return fridge.getContenu();
    }

    public List<Tag> getTagsByFridge(UUID userId) {
        Fridge fridge = getFridgeByUserId(userId);
        return fridge.getTags();
    }
}
