package com.niceshop.niceshop.service;

import com.niceshop.niceshop.DTO.UserDTO;
import com.niceshop.niceshop.exceptions.EmailAlreadyExists;
import com.niceshop.niceshop.exceptions.UserAlreadyExists;
import com.niceshop.niceshop.model.User;
import com.niceshop.niceshop.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public Map<String,String> registerNewUser(UserDTO userDTO) {
        Map<String,String> messages = new HashMap<>();
        if (userRepo.findByUsername(userDTO.getUsername()).isPresent()) {
            //throw new UserAlreadyExists("User with that username already exists!");
            messages.put("usernameMessage", "User with that username exists!");
        }
        if (userRepo.findByEmail(userDTO.getEmail()).isPresent()) {
            //throw new EmailAlreadyExists("User with that email already exists!");
            messages.put("emailMessage", "User with that email exists!");
        }
        if (messages.isEmpty()) {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            userRepo.save(user);
            return null;
        }
        return messages;
    }
}
