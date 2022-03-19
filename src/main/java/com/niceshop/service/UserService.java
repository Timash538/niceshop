package com.niceshop.service;

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

    public User findByUsername(String username) {return userRepo.findByUsername(username);}

    public boolean isUserInDB(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null || userRepo.findByEmail(user.getEmail()) != null) {return true;}
        else {return false;}
    }
    //Checks if user is in DataBase and returns map with violations
    public Map<String,String> getUserRepoErrorsMap(User user) {
        Map<String,String> errorMap = new HashMap<>();
        if (userRepo.findByUsername(user.getUsername()) != null) {
            errorMap.put("usernameMessage", "User with that username already exists!");
        }
        if (userRepo.findByEmail(user.getEmail()) != null) {
            errorMap.put("emailMessage", "User with that email already exists!");
        }
        return errorMap;
    }

    public void registerNewUser(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(true);
            userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
