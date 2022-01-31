package com.niceshop.controllers;

import com.niceshop.model.User;
import com.niceshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    String registration(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    String registrationProcess(@ModelAttribute("user") @Valid User user,
                                     BindingResult bindingResult,
                                     Model model) {
        boolean isPasswordConfirmationCorrect = user.getPassword().equals(user.getConfirmationPassword());
        if (!isPasswordConfirmationCorrect) {
            model.addAttribute("passwordConfirmationMessage", "Passwords doesn't match!");
        }

        if (bindingResult.hasErrors() || !isPasswordConfirmationCorrect || userService.isUserInDB(user)) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            errorsMap.keySet().iterator().forEachRemaining(System.out::println);

            errorsMap = userService.getUserRepoErrorsMap(user);
            model.mergeAttributes(errorsMap);

            return "registration";
        }

        userService.registerNewUser(user);
        return "login";
    }




}
