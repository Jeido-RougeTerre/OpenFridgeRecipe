package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.entity.Ingredient;
import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.repository.TagRepository;
import com.jeido.openfridgerecipe.entity.Tag;
import com.jeido.openfridgerecipe.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagsRepository;

    public List<Tag> compute(List<Tag> tags) {
        List<String> noTags = new ArrayList<>();
        List<Tag> result = new ArrayList<>();
        for (Tag tag : tags) {
            if (!result.contains(tag) && !noTags.contains(tag.getName())) {
                String str = tag.getName().split(":")[1];
                if (str.startsWith("no-")) {
                    noTags.add(str);
                }
                result.add(tag);
            }
        }

        result.removeIf(tag -> !noTags.contains("no-" + tag.getName().split(":")[1]));

        return result;
    }

    public List<Tag> computeFromIngredients(List<Ingredient> ingredients) {
        List<Tag> result = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            result.addAll(ingredient.getTags());
        }

        return compute(result);
    }

    public List<Tag> computeForRecipe(Recipes recipes) {
        return computeFromIngredients(recipes.getIngredients());
    }


    public Tag parseOrCreate(String name) {
        if (tagsRepository.existsTagsByName(name)) {
            return findByName(name);
        }
        return create(Tag.builder().name(name).build());
    }

    @Autowired
    public TagService(TagRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public Tag create(Tag tags) {
        if (tagsRepository.existsTagsByName(tags.getName())) return null;
        return tagsRepository.save(tags);
    }

    public Tag findByName(String str) {
        return tagsRepository.findByName(str).orElseThrow(() -> new NotFoundException("Tag not found with name " + str));
    }

    public Tag update(Tag tags) {
        if (!tagsRepository.existsTagsByName(tags.getName())) return null;
        return tagsRepository.save(tags);
    }

    public boolean delete(Tag tags) {
        if (!tagsRepository.existsTagsByName(tags.getName())) return false;
        tagsRepository.delete(tags);
        return true;
    }

    public boolean existByName(String name) {
        return tagsRepository.existsTagsByName(name);
    }



}
