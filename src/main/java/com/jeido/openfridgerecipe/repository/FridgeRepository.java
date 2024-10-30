package com.jeido.openfridgerecipe.repository;

import com.jeido.openfridgerecipe.entity.Fridge;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FridgeRepository extends CrudRepository<Fridge, Long> {

    Optional<Fridge> findByUserid(UUID userid);
}
