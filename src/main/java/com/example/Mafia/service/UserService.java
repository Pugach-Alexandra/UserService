package com.example.Mafia.service;


import com.example.Mafia.model.User;
import com.example.Mafia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class UserService  {

private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateById(Long userId, User user) {
        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        if (updatableUser.isPresent()) {
            if(user.getName() !=null)
            newUser.setName(user.getName());
            if(user.getBandId() !=null)
            newUser.setBandId(user.getBandId());
            if(user.getTaskId() !=null)
            newUser.setTaskId(user.getTaskId());
        }
        return userRepository.save(newUser);
    }

    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }

    public Object updateBandId(Long userId, String bandName) {
        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        String baseUrl = "https://heroku-bands.herokuapp.com/bands?bandName=" + bandName;
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
        try {
            String jsonStr = new String((response.getBody()).getBytes());
            JSONObject jsonObject = new JSONObject(jsonStr);
            Long bandId =  Long.valueOf(jsonObject.getString("id"));
            newUser.setBandId(bandId);
            return userRepository.save(newUser);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public Object updateTaskId(Long userId, String taskName) {
        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        String baseUrl = "https://test-herokus1.herokuapp.com/tasks?taskName=" + taskName;
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
        try {
            String jsonStr = new String((response.getBody()).getBytes());
            JSONObject jsonObject = new JSONObject(jsonStr);
            Long taskId =  Long.valueOf(jsonObject.getString("id"));
            newUser.setTaskId(taskId);
            return userRepository.save(newUser);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
