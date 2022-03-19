package com.niceshop.controllers;

import com.niceshop.model.Product;
import com.niceshop.model.User;
import com.niceshop.service.ProductService;
import com.niceshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.color.ProfileDataException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user, Model model) {
        List<Product> products = new ArrayList<>();
        for (Product product : productService.findAll()) {
            products.add(product);
        }
        model.addAttribute("products",products);
        model.addAttribute("user",user);
        return "main";
    }
}
