package com.project.journalApp.controller;

import com.project.journalApp.entity.User;
import com.project.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping
    private ResponseEntity<?> getAllUsers(){
        try {
            List<User> all = userService.getAll();
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    private ResponseEntity<?> createAdmin(@RequestBody User user){
        userService.addAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
