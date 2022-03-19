package com.niceshop.controllers;

import com.niceshop.model.Role;
import com.niceshop.model.User;
import com.niceshop.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(@AuthenticationPrincipal User user,
                           Model model) {
        model.addAttribute("userList",userRepo.findAll());
        model.addAttribute("user", user);
        return "users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id,
                          @ModelAttribute("user") User user,
                          @AuthenticationPrincipal User currentUser,
                          Model model) {
        if (!userRepo.findById(id).isPresent() || !(currentUser.isAdmin() || currentUser.getId().equals(id))) {
            return "redirect:/";
        }
        user = userRepo.findById(id).get();
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User editedUser,
                             @AuthenticationPrincipal User currentUser) {
        User user;
        if (userRepo.findById(id).isPresent()) {
            user = userRepo.findById(id).get();
        }
        else {
            return "redirect:/";
        }
        if ((currentUser.hasRole(Role.ADMIN)) || currentUser.getId().equals(user.getId())) {
            user.setUsername(editedUser.getUsername());
            user.setEmail(editedUser.getEmail());
            user.setFirstname(editedUser.getFirstname());
            user.setLastname(editedUser.getLastname());
            if (currentUser.getId().equals(user.getId())) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                User userDetails = (User) authentication.getPrincipal();
                userDetails.setUsername(user.getUsername());
            }
            userRepo.save(user);
        }
        return "redirect:/user/" + user.getId();
    }

}
