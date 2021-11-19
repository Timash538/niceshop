package com.niceshop.niceshop.controllers;

import com.niceshop.niceshop.DTO.UserDTO;
import com.niceshop.niceshop.exceptions.EmailAlreadyExists;
import com.niceshop.niceshop.exceptions.UserAlreadyExists;
import com.niceshop.niceshop.model.User;
import com.niceshop.niceshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    String main() {
        return "main";
    }

    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    RedirectView loggingIn(@RequestParam String username,
                           @RequestParam String password) {

        return new RedirectView("/");
    }

    @GetMapping("/registration")
    String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    ModelAndView registrationProcess(@ModelAttribute @Valid UserDTO user,
                                     RedirectAttributes attributes) {
        Map<String,String> messages = userService.registerNewUser(user);
        if (!messages.isEmpty()) {
            for (String key : messages.keySet()) {
                attributes.addFlashAttribute(key, messages.get(key));
            }
            return new ModelAndView("redirect:/registration");
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/main");
        modelAndView.addObject("newUserMessage", "Welcome aboard!");
        return modelAndView;
    }
}
