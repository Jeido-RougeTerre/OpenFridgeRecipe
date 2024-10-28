package com.jeido.openfridgerecipe.repository;

import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    List<Ingredient> findByTags(List<Tag> tags);
    List<Ingredient> findByCalories(double calories);
    List<Ingredient> findByCaloriesBetween(double min, double max);
    List<Ingredient> findByCaloriesGreaterThan(double calories);
    List<Ingredient> findByCaloriesLessThan(double calories);}
