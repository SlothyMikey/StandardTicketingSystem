package com.mikey.ticketing_api.controller;

import com.mikey.ticketing_api.dto.UserRequest;
import com.mikey.ticketing_api.model.User;
import com.mikey.ticketing_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest request){
        User userCreated = userService.createUser(request);
        URI location = URI.create("/api/users/" + userCreated.getUserId());
        return ResponseEntity.created(location).body(userCreated);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

}
