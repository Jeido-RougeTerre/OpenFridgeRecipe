package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.dto.SearchDTOSend;
import com.jeido.openfridgerecipe.repository.IngredientRepository;
import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Tag;
import com.jeido.openfridgerecipe.service.json.*;
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

    private static final String PRE_API_URI_CODE = "https://world.openfoodfacts.net/api/v2/product/";
    private static final String POST_API_URI_CODE = "?fields=product_name,nutriments,quantity,labels_tags,allergens_tags";

    private static final String PRE_API_URI_SEARCH = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=";
    private static final String POST_API_URI_SEARCH = "&search_simple=1&action=process&json=1";

    private Ingredient mapToIngredient(IngredientAPIResponse body) {
        Product p = body.getProduct();
        List<String> tagNames = new ArrayList<>();
        if (p.getAllergensTags() != null) {
            tagNames.addAll(Arrays.stream(p.getAllergensTags()).toList());
        }
        if (p.getLabelsTags() != null) {
            tagNames.addAll(Arrays.stream(p.getLabelsTags()).toList());
        }

        List<Tag> tagList = new ArrayList<>();

        for (String tag : tagNames) {
            tagList.add(tagService.parseOrCreate(tag));
        }
        return Ingredient.builder()
                .code(body.getCode())
                .name(p.getProductName())
                .quantity(p.getQuantity())
                .calories(p.getNutriments().getEnergyKcal())
                .tags(tagList)
                .build();
    }

    private SearchDTOSend mapToIngredientList(IngredientSearchAPIResponse body) {
        List<Ingredient> ingList = new ArrayList<>();


        for (ProductSearch product : body.getProducts()) {
            Ingredient ing = getIngredientByCode(product.getId());
            if (ing != null) ingList.add(ing);
        }

        int prevPage = (body.getPage() > 1)? body.getPage() - 1 : 0;
        int totalPage = (int)Math.ceil((double)body.getCount() / body.getPageSize());
        int nextPage = (body.getPage() == totalPage)? 0 : body.getPage() + 1;


        return SearchDTOSend.builder()
                .count(body.getCount())
                .page(body.getPage())
                .prevPage(prevPage)
                .nextPage(nextPage)
                .pageSize(body.getPageSize())
                .pageCount(body.getPageCount())
                .totalPages(totalPage)
                .results(ingList)
                .build();

    }

    public Ingredient getIngredientByCode(String code) {
        System.out.println(code + " is in DB : " + ingredientRepository.findById(code).isPresent());

        if (ingredientRepository.findById(code).isPresent()) return ingredientRepository.findById(code).get();

        return getIngredientByCodeInAPI(code);
    }

    private Ingredient getIngredientByCodeInAPI(String code) {
        final String uri = PRE_API_URI_CODE + code + POST_API_URI_CODE;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<IngredientAPIResponse> response = restTemplate.getForEntity(uri, IngredientAPIResponse.class);
            Ingredient ing = mapToIngredient(Objects.requireNonNull(response.getBody()));

            if (ing != null && !ingredientRepository.existsById(code)) {
                ingredientRepository.save(ing);
            }

            return ing;
        } catch (Exception e) {
            return null;
        }
    }

    public SearchDTOSend getIngredientsByName(String name) {
        return getIngredientsByName(name, 1);
    }


    public SearchDTOSend getIngredientsByName(String name, int page) {
        final String uri = PRE_API_URI_SEARCH + name + "&page=" + page + POST_API_URI_SEARCH;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IngredientSearchAPIResponse> response = restTemplate.getForEntity(uri, IngredientSearchAPIResponse.class);
        SearchDTOSend search = mapToIngredientList(Objects.requireNonNull(response.getBody()));
        search.setSearchedTerm(name);
        return search;
    }

    public List<Ingredient> getAllIngredients() {
        return (List<Ingredient>) ingredientRepository.findAll();
    }

    public boolean deleteIngredientByCode(String code) {
        if (!ingredientRepository.existsById(code)) return false;
        ingredientRepository.deleteById(code);
        return true;
    }

    public List<Ingredient> findByTags(List<Tag> tags) {
        return ingredientRepository.findByTags(tags);
    }

    public List<Ingredient> findByTagsName(List<String> tags) {
        List<Tag> tagList = new ArrayList<>();
        for (String tag : tags) {
            if (tagService.existByName(tag)) {
                tagList.add(tagService.findByName(tag));
            }
        }

        return findByTags(tagList);
    }

    public List<Ingredient> findByCalories(double calories) {
        return ingredientRepository.findByCalories(calories);
    }

    public List<Ingredient> findByCalories(double caloriesMin, double caloriesMax) {
        if (caloriesMin > caloriesMax) {
            double temp = caloriesMax;
            caloriesMax = caloriesMin;
            caloriesMin = temp;
        }

        if (caloriesMin == 0 && caloriesMax == 0) {
            return findByCalories(0);
        }

        if (caloriesMin == 0) {
            return ingredientRepository.findByCaloriesLessThan(caloriesMax);
        }

        if (caloriesMax == 0) {
            return ingredientRepository.findByCaloriesGreaterThan(caloriesMin);
        }

        return ingredientRepository.findByCaloriesBetween(caloriesMin, caloriesMax);

    }



}
