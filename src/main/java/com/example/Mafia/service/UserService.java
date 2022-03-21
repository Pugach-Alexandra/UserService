package com.example.Mafia.service;


import com.example.Mafia.configuration.ServicesConnection;
import com.example.Mafia.model.User;
import com.example.Mafia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Service
public class UserService  {

private final UserRepository userRepository;
private final RestTemplate restTemplate;
private final ServicesConnection connection;

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate, ServicesConnection connection) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.connection = connection;
    }

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

    public User updateBandId(Long userId, String bandName) {

        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        ResponseEntity<String> response = restTemplate.exchange(connection.getUrlBands() + bandName, HttpMethod.GET, null, String.class);

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

    public User updateTaskId(Long userId, String taskName) {

        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        ResponseEntity<String> response = restTemplate.exchange(connection.getUrlTasks() + taskName, HttpMethod.GET, null, String.class);

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
