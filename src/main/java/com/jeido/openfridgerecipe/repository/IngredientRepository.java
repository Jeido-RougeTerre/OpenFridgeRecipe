package com.jeido.openfridgerecipe.repository;

import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    Optional<Ingredient> findByName(String name);
    List<Ingredient> findByTags(List<Tag> tags);

    List<Ingredient> findByCalories(double calories);
    List<Ingredient> findByCaloriesBetween(double min, double max);
    List<Ingredient> findByCaloriesGreaterThan(double calories);
    List<Ingredient> findByCaloriesLessThan(double calories);}
