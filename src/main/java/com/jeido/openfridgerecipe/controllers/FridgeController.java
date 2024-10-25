package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.entity.User;
import com.jeido.openfridgerecipe.service.FridgeService;
import com.jeido.openfridgerecipe.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fridges")
public class FridgeController {

    private final FridgeService fridgeService;
    private final IngredientService ingredientService;

    @Autowired
    public FridgeController(FridgeService fridgeService, IngredientService ingredientService) {
        this.fridgeService = fridgeService;
        this.ingredientService = ingredientService;
    }
}
