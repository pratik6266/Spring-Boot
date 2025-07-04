package com.project.journalApp.controller;

import com.project.journalApp.entity.User;
import com.project.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User existingUser = userService.getUserByUsername(user.getUsername());
            if (existingUser != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            userService.saveUser(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId userId){
        try {
            if(userId == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User data = userService.getUser(userId);
            if(data == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<User>(data, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{userId}")
    public ResponseEntity<User> updateUserById(@PathVariable ObjectId userId, @RequestBody User user){
        try{
            if(userId == null || user == null || user.getUsername() == null || user.getPassword() == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User existingUser = userService.getUser(userId);
            if(existingUser == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            userService.saveUser(existingUser);
            return new ResponseEntity<User>(existingUser, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable ObjectId userId){
        try {
            if(userId == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User existingUser = userService.getUser(userId);
            if(existingUser == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userService.deleteById(userId);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
