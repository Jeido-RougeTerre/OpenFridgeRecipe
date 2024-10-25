package com.jeido.openfridgerecipe.repository;

import com.jeido.openfridgerecipe.entity.Tags;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagsRepository extends CrudRepository<Tags, UUID> {
    Optional<Tags> findByName(String name);
    boolean existsTagsByName(String name);
}
