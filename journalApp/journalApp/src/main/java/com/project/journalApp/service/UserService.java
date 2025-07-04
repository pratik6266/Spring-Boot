package com.project.journalApp.service;

import com.project.journalApp.entity.User;
import com.project.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User getUser(ObjectId userId){
        return userRepository.findById(userId).orElse(null);
    }

    public void deleteById(ObjectId userId){
        userRepository.deleteById(userId);
    }
}
