package com.niceshop.service;

import com.niceshop.DTO.UserDTO;
import com.niceshop.model.Role;
import com.niceshop.model.User;
import com.niceshop.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

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

    //Checks if user is in DataBase and returns map with violoations
    public Map<String,List<String>> isUserInDB(UserDTO userDTO) {
        Map<String,List<String>> messages = new HashMap<>();
        if (userRepo.findByUsername(userDTO.getUsername()) != null) {
            //throw new UserAlreadyExists("User with that username already exists!");
            List<String> list = new ArrayList<>();
            list.add("User with that username exists!");
            messages.put("usernameMessage", list);
        }
        if (userRepo.findByEmail(userDTO.getEmail()).isPresent()) {
            //throw new EmailAlreadyExists("User with that email already exists!");
            List<String> list = new ArrayList<>();
            list.add("User with that email exists!");
            messages.put("emailMessage", list);
        }
        return messages;
    }

    public void registerNewUser(UserDTO userDTO) {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(true);
            userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
