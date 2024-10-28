package com.jeido.openfridgerecipe.repository;

import com.jeido.openfridgerecipe.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagsRepository extends CrudRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);
    boolean existsTagsByName(String name);
}
