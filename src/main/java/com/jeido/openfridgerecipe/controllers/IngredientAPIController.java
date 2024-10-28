package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.dto.SearchDTOSend;
import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/ingredient/")
public class IngredientAPIController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientAPIController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("{code}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("code") String code) {
        Ingredient ing = ingredientService.getIngredientByCode(code);


        return ResponseEntity.ok(ing);
    }

    @GetMapping("/search/{terms}")
    public ResponseEntity<SearchDTOSend> getIngredientsByTerms(@PathVariable("terms") String terms) {
        return ResponseEntity.ok(ingredientService.getIngredientsByName(terms));
    }

    @GetMapping("/search/{terms}/{page}")
    public ResponseEntity<SearchDTOSend> getIngredientsByTerms(@PathVariable("terms") String terms, @PathVariable("page") int page) {
        return ResponseEntity.ok(ingredientService.getIngredientsByName(terms,page));
    }

    @GetMapping("/tags/{tags}")
    public ResponseEntity<List<Ingredient>> getIngredientsByTags(@PathVariable("tags") String tags) {
        String[] str = tags.split(",");
        return ResponseEntity.ok(ingredientService.findByTagsName(Arrays.stream(str).toList()));
    }

}
