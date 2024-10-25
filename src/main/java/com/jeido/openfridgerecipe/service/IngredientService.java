package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.entity.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Service
public class IngredientService {

    private static final String API_URI = "https://world.openfoodfacts.net/api/v2/";

    private Ingredient mapToIngredient(IngredientAPIResponse ingredientAPIResponse) {
        return Ingredient.builder()
                .name(ingredientAPIResponse.getProduct().getProductName())
                .code(ingredientAPIResponse.getCode())
                .quantity(ingredientAPIResponse.getProduct().getQuantity())
                .calories(ingredientAPIResponse.getProduct().getNutriments().getEnergyKcal())
                .build();
    }

    public Ingredient getIngredientByCode(String code) {
        final String uri = API_URI + "product/" + code + "?fields=product_name,nutriments,quantity";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IngredientAPIResponse> response = restTemplate.getForEntity(uri, IngredientAPIResponse.class);
        return mapToIngredient(Objects.requireNonNull(response.getBody()));
    }



}
