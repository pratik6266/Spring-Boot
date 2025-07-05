package com.project.journalApp.controller;

import com.project.journalApp.entity.User;
import com.project.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/user-update")
    public ResponseEntity<User> updateUserById(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        try{
            if(username == null || user == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User existingUser = userService.getUserByUsername(username);
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

    @DeleteMapping("/user-delete")
    public ResponseEntity<Boolean> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            User user = userService.getUserByUsername(username);
            if(user == null){
                return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
            }
            userService.deleteUserByUsername(username);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
