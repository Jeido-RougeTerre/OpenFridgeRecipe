package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.repository.IngredientRepository;
import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Tags;
import com.jeido.openfridgerecipe.service.json.IngredientAPIResponse;
import com.jeido.openfridgerecipe.service.json.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
public class IngredientService {

    public final TagService tagService;
    public final IngredientRepository ingredientRepository;

    public IngredientService(TagService tagService, IngredientRepository ingredientRepository) {
        this.tagService = tagService;
        this.ingredientRepository = ingredientRepository;
    }

    private static final String API_URI = "https://world.openfoodfacts.net/api/v2/";

    private Ingredient mapToIngredient(IngredientAPIResponse ingredientAPIResponse) {
        Product p = ingredientAPIResponse.getProduct();
        List<String> tagNames = new ArrayList<>();
        if (p.getAllergensTags() != null) {
            tagNames.addAll(Arrays.stream(p.getAllergensTags()).toList());
        }
        if (p.getLabelsTags() != null) {
            tagNames.addAll(Arrays.stream(p.getLabelsTags()).toList());
        }

        List<Tags> tagList = new ArrayList<>();

        for (String tag : tagNames) {
            tagList.add(tagService.parseOrCreate(tag));
        }
        return Ingredient.builder()
                .code(ingredientAPIResponse.getCode())
                .name(p.getProductName())
                .quantity(p.getQuantity())
                .calories(p.getNutriments().getEnergyKcal())
                .tags(tagList)
                .build();
    }

    public Ingredient getIngredientByCode(String code) {
        return ingredientRepository.findById(code).orElse(getIngredientByCodeInAPI(code));
    }

    private Ingredient getIngredientByCodeInAPI(String code) {
        final String uri = API_URI + "product/" + code + "?fields=product_name,nutriments,quantity,labels_tags,allergens_tags";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IngredientAPIResponse> response = restTemplate.getForEntity(uri, IngredientAPIResponse.class);
        Ingredient ing = mapToIngredient(Objects.requireNonNull(response.getBody()));
        if (!ingredientRepository.existsById(code)) {
            ingredientRepository.save(ing);
        }

        return ing;
    }

    public List<Ingredient> getAllIngredients() {
        return (List<Ingredient>) ingredientRepository.findAll();
    }

    public boolean deleteIngredientByCode(String code) {
        if (!ingredientRepository.existsById(code)) return false;
        ingredientRepository.deleteById(code);
        return true;
    }



}
