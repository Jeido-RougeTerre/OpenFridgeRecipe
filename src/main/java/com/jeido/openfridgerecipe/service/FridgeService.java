package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Fridge;
import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.entity.User;
import com.jeido.openfridgerecipe.repository.FridgeRepository;
import com.jeido.openfridgerecipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final UserRepository userRepository;
    private final RecipesService recipesService;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, UserRepository userRepository, RecipesService recipesService) {
        this.fridgeRepository = fridgeRepository;
        this.userRepository = userRepository;
        this.recipesService = recipesService;
    }

    public Fridge getFridgeByUserId(Long userId) {
        return fridgeRepository.findByUser_Id(userId).orElseThrow(() -> new RuntimeException("Fridge not found for this User"));
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

}
