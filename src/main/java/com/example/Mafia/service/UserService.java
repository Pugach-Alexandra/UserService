package com.example.Mafia.service;

import com.example.Mafia.model.User;
import com.example.Mafia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {

private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public Object updateBandId(Long userId, Long bandId) {
        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        if (updatableUser.isPresent()) {
            newUser.setBandId(bandId);
        }
        return userRepository.save(newUser);
    }

    public Object updateTaskId(Long userId, Long taskId) {
        Optional<User> updatableUser = userRepository.findById(userId);
        User newUser = updatableUser.get();
        if (updatableUser.isPresent()) {
            newUser.setTaskId(taskId);
        }
        return userRepository.save(newUser);
    }
}
