package com.jeido.openfridgerecipe.repository;

import com.jeido.openfridgerecipe.entity.Fridge;
import com.jeido.openfridgerecipe.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FridgeRepository extends CrudRepository<Fridge, Long> {

    Optional<Fridge> findByUser(User user);
}
