package com.jeido.openfridgerecipe.service;


import com.jeido.openfridgerecipe.entity.*;
import com.jeido.openfridgerecipe.exception.NotFoundException;
import com.jeido.openfridgerecipe.repository.FridgeRepository;
import com.jeido.openfridgerecipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final RecipesService recipesService;
    private final UserRepository userRepository;


    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, RecipesService recipesService, UserRepository userRepository) {
        this.fridgeRepository = fridgeRepository;

        this.recipesService = recipesService;
        this.userRepository = userRepository;
    }

    public Fridge getFridgeByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Could not found user with id #" + userId));
        return fridgeRepository.findByUser(user).orElseThrow(() -> new NotFoundException("Fridge not found for this User"));
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
        Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new NotFoundException("Fridge not found with id: " + fridgeId));
        return fridge.getContenu();
    }

    public List<Recipes> getSuggestedRecipes(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        Fridge fridge = fridgeRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Fridge not found for user id: " + userId));

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
