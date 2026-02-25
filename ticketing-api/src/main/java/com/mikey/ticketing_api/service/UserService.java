package com.mikey.ticketing_api.service;

import com.mikey.ticketing_api.UserRoles;
import com.mikey.ticketing_api.dto.UserRequest;
import com.mikey.ticketing_api.model.User;
import com.mikey.ticketing_api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(UserRequest userRequest){
        User user = new User();
        user.setFull_name(userRequest.full_name());
        user.setEmail(userRequest.email());
        user.setPassword_hash(
                hashedPassword(userRequest.password()
                ));
        user.setRole(UserRoles.CUSTOMER.getRole());
        user.setCreated_at(OffsetDateTime.now());
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }



    public static String hashedPassword(String password){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }


}
