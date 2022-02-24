package com.example.Mafia.controller;

import com.example.Mafia.model.User;
import com.example.Mafia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://heroku-bands.herokuapp.com/bands?bandName=" + bandName;
        ResponseEntity<String> response = null;
        response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
        logger.info("Updating the User with id: " +id);
        try {
            String jsonStr = new String((response.getBody()).getBytes());
            JSONObject jsonObject = new JSONObject(jsonStr);
            Long bandId =  Long.valueOf(jsonObject.getString("id"));
            return ok(userService.updateBandId(id, bandId));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
