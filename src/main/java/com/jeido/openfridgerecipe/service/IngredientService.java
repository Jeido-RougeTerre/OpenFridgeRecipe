package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.entity.Ingredient;
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

    private static final String API_URI = "https://world.openfoodfacts.net/api/v2/";

    private Ingredient mapToIngredient(IngredientAPIResponse ingredientAPIResponse) {
        Product p = ingredientAPIResponse.getProduct();
        List<String> tags = new ArrayList<>();
        if (p.getAllergensTags() != null) {
            tags.addAll(Arrays.stream(p.getAllergensTags()).toList());
        }
        if (p.getLabelsTags() != null) {
            tags.addAll(Arrays.stream(p.getLabelsTags()).toList());
        }
        return Ingredient.builder()
                .code(ingredientAPIResponse.getCode())
                .name(p.getProductName())
                .quantity(p.getQuantity())
                .calories(p.getNutriments().getEnergyKcal())
                .tags(tags.toArray(new String[0]))
                .build();
    }

    public Ingredient getIngredientByCode(String code) {
        final String uri = API_URI + "product/" + code + "?fields=product_name,nutriments,quantity,labels_tags";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IngredientAPIResponse> response = restTemplate.getForEntity(uri, IngredientAPIResponse.class);
        return mapToIngredient(Objects.requireNonNull(response.getBody()));
    }



}
