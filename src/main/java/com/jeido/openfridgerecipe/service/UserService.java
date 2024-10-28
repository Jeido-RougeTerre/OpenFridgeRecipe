package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.entity.Recipes;
import com.jeido.openfridgerecipe.entity.Tag;
import com.jeido.openfridgerecipe.entity.User;
import com.jeido.openfridgerecipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private com.jeido.openfridgerecipe.entity.Ingredient Ingredient;
    private Tag Tags;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
    }


    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }


    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }


    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        return userRepository.save(user);
    }


    public User updateUser(UUID id, User userDetails) {
        return null;
    }


    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }


    public void addFavoriteRecipe(UUID userId, Recipes recipe) {
        User user = getUserById(userId);
        user.addFavoriteRecipe(recipe);
        if (recipe != null && !user.getRecettesFav().contains(recipe)) {
            user.getRecettesFav().add(recipe);
            userRepository.save(user);
        }
    }

    
    public void addDieteticIngredient(UUID userId, Tag tag){
        User user = getUserById(userId);
        if (!user.getIngredientsDietetique().contains(tag)) {
            user.getIngredientsDietetique().add(tag);
            userRepository.save(user);
        }
    }

}



