package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.dto.UserDtoReceive;
import com.jeido.openfridgerecipe.dto.UserDtoRegister;
import com.jeido.openfridgerecipe.dto.UserDtoSend;
import com.jeido.openfridgerecipe.entity.User;
import com.jeido.openfridgerecipe.exception.NotFoundException;
import com.jeido.openfridgerecipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDtoSend> getAllUsers() {
        return ((List<User>) userRepository.findAll()).stream().map(this::userToSend).toList();
    }


    public UserDtoSend getUserById(UUID id) {
        return userToSend(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id)));
    }


    public UserDtoSend createUser(UserDtoRegister user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }


        return userToSend(userRepository.save(
                User.builder()
                        .email(user.getEmail())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .password(user.getPassword())
                        .favoriteRecipe(new ArrayList<>())
                        .build()
        ));
    }


    public UserDtoSend updateUser(UUID id, UserDtoReceive userDetails) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id " + id));
        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setFavoriteRecipe(userDetails.getFavoriteRecipes());

        return userToSend(userRepository.save(user));
    }


    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDtoSend userToSend(User user) {
        return UserDtoSend.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .isAdmin(user.isAdmin())
                .favoriteRecipes(user.getFavoriteRecipe())
                .build();
    }

    public UserDtoSend login(UserDtoReceive userDetails) {
        if (!userRepository.existsByEmail(userDetails.getEmail())) return null;
        User user = userRepository.findByEmail(userDetails.getEmail()).get();
        if (!user.getPassword().equals(userDetails.getPassword())) return null;

        return userToSend(user);
    }
}



