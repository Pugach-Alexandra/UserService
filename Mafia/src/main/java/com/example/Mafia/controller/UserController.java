package com.example.Mafia.controller;

import com.example.Mafia.model.User;
import com.example.Mafia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Controller
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(User user){
        logger.info("Creating a User");
        return ok(userService.createUser(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){
        logger.info("Getting all Users");
        return ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable("id") Long id){
        logger.info("Getting the User with id: " +id);
        return ok(userService.findById(id));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, User user){
        logger.info("Updating the User with id: " +id);
        return ok(userService.updateById(id, user));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        logger.info("Deleting the User with id: " +id);
        userService.deleteById(id);
    }

}
