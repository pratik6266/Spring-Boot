package com.project.journalApp.controller;

import com.project.journalApp.entity.User;
import com.project.journalApp.service.ApiService;
import com.project.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApiService apiService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/user-create")
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
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.error("Error creating user: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> apiCheck(){
      String data = apiService.getApi();
      return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
