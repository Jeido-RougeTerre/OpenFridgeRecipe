package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.repository.TagRepository;
import com.jeido.openfridgerecipe.entity.Tag;
import com.jeido.openfridgerecipe.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagsRepository;

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
