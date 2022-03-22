package com.niceshop.controllers;

import com.niceshop.model.Product;
import com.niceshop.model.Role;
import com.niceshop.model.User;
import com.niceshop.service.ProductService;
import com.niceshop.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public String showProducts(@AuthenticationPrincipal User user,
                               Model model) {
        List<Product> products = new ArrayList<>();
        for (Product product : productService.findByUser(user)) {
            products.add(product);
        }
        model.addAttribute("products",products);
        model.addAttribute("user",user);
        return "user_products";
    }

    @GetMapping("/edit/{id}")
    public String showProduct(@AuthenticationPrincipal User user,
                              @PathVariable("id") Long id,
                              Model model) {
        Product product = productService.findById(id).get();

        model.addAttribute("product", product);
        model.addAttribute("user", user);
        return "edit_product";
    }

    @GetMapping("/new_product")
    public String productForm(@ModelAttribute("product") Product product,
                              @AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user",user);
        return "register_new_product";
    }

    @PostMapping
    public String registerNewProduct(@ModelAttribute("product") @Valid Product product,
                                     BindingResult bindingResult,
                                     @RequestParam("image") MultipartFile[] multipartFile,
                                     @AuthenticationPrincipal User currentUser,
                                     Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "register_new_product";
        }

        String uploadDir = "sss/" + currentUser.getId() + "/products/" + (productService.getNextSeriesId());
            Arrays.asList(multipartFile).stream().forEach(file -> {
                String extension = file.getOriginalFilename();
                extension = extension.substring(extension.lastIndexOf('.'));
                String filename = product.addPicture((product.getPictures().size())+extension);
                try {
                    FileUploadUtil.saveFile(uploadDir, filename, file);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            });
        product.setUser(currentUser);
        productService.registerNewProduct(product);

        return "redirect:/product";
    }

}
