package com.jeido.openfridgerecipe.repository;

import com.jeido.openfridgerecipe.entity.Fridge;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public abstract class FridgeRepository implements CrudRepository<Fridge, Long> {

    public abstract Optional<Fridge> findByUser_Id(Long userId);
}
