package com.jeido.openfridgerecipe.repository;

import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.entity.Tags;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RecipesRepository extends CrudRepository<Recipes, UUID> {
    List<Recipes> findByName(String name);
    List<Recipes> findByDieteticAlignment(Tags tag);
}
