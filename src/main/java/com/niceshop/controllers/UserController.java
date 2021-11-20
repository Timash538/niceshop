package com.niceshop.controllers;

import com.niceshop.DTO.UserDTO;
import com.niceshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.*;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
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

        //Find all violations with hibernate validation features
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        Map<String, List<String>> messages = userService.isUserInDB(user);
        //If hibernate validation found violations or user is in DB
        if (!violations.isEmpty() || !messages.isEmpty()) {

            // For each violation add flash attribute
            for (ConstraintViolation violation : violations) {
                String messageName = violation.getPropertyPath().iterator().next().getName() + "Message";
                if (messages.containsKey(messageName)) {
                    messages.get(messageName).add(violation.getMessage());
                }
                else {
                    List<String> stringList = new ArrayList<>();
                    stringList.add(violation.getMessage());
                    messages.put(messageName, stringList);
                }
            }
            for (String message : messages.keySet()) {
                System.out.println(message + " : " + messages.get(message));
                attributes.addFlashAttribute(message, messages.get(message));
            }

            attributes.addFlashAttribute("username", user.getUsername());
            attributes.addFlashAttribute("email",user.getEmail());
            return new ModelAndView("redirect:/registration");
        }

        userService.registerNewUser(user);
        return new ModelAndView("main");
    }
}
