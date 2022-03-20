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
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        logger.info("Creating a User");
        return ok(userService.createUser(user));

    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){

        logger.info("Getting all Users");
        return ok(userService.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable("id") Long id){

        logger.info("Getting the User with id: " +id);
        return ok(userService.findById(id));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){

        logger.info("Updating the User with id: " +id);
        return ok(userService.updateById(id, user));

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){

        logger.info("Deleting the User with id: " + id);
        userService.deleteById(id);

    }

    @PatchMapping("/{id}/addBand")
    public ResponseEntity<Object> updateUsersBand(@PathVariable("id") Long id, @RequestBody String bandName){

        logger.info("Updating the Users band with id: " +id + " to: " +  bandName);
        return ok(userService.updateBandId(id, bandName));

    }

    @PatchMapping("/{id}/addTask")
    public ResponseEntity<Object> updateUsersTask(@PathVariable("id") Long id, @RequestBody String taskName){

        logger.info("Updating the Users task with id: " +id + " to: " +  taskName);
        return ok(userService.updateTaskId(id, taskName));

    }

}
