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
import java.nio.file.Paths;
import java.nio.file.Path;
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
    public String editProductForm(@AuthenticationPrincipal User user,
                              @PathVariable("id") Product product,
                              Model model) {
        if (!(user.isAdmin() || user == product.getUser()))
        return "redirect:/";
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
        int filesCount = multipartFile.length;

        if (bindingResult.hasErrors() || filesCount>5) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            if (filesCount>2) {
                model.addAttribute("filesMessage", "Maximum 5 files");
            }
            model.addAttribute("user",currentUser);
            return "register_new_product";
        }

        String uploadDir = "imageRepo/" + currentUser.getId() + "/products/" + (productService.getNextSeriesId());
        System.out.println(uploadDir);
            Arrays.asList(multipartFile).stream().forEach(file -> {
                if (!file.isEmpty()) {
                    String extension = file.getOriginalFilename();
                    extension = extension.substring(extension.lastIndexOf('.'));
                    String filename = product.addPicture((product.getPictures().size()) + extension);
                    try {
                        FileUploadUtil.saveFile(uploadDir, filename, file);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            });
        product.setUser(currentUser);
        productService.save(product);
        return "redirect:/product";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product,
                              @PathVariable("id") Long id,
                              @AuthenticationPrincipal User currentUser,
                              Model model) {
        model.addAttribute("user",currentUser);
        if (!(currentUser.hasRole(Role.ADMIN) || product.getUser() == currentUser)) {
            model.addAttribute("userMessage","User is not owner of product or administrator");
            return "edit_product";
        }
        Product currentProduct = productService.findById(id).get();
        product.setUser(currentProduct.getUser());
        product.setPictures(currentProduct.getPictures());
        productService.save(product);
        return "edit_product";
    }

    @PostMapping("/edit/{productId}/{pictureId}")
    public String editPicture(@PathVariable("productId") Product product,
                            @PathVariable("pictureId") int pictureId,
                            @RequestParam("image") MultipartFile file,
                            @AuthenticationPrincipal User user) {
        String uploadDir = "imageRepo/" + product.getUser().getId() + "/products/" + (product.getId());
        String extension = file.getOriginalFilename();
        extension = extension.substring(extension.lastIndexOf('.'));
        String filename = product.getPictures().get(pictureId).charAt(0) + extension;
        try {
            FileUploadUtil.deleteFile(uploadDir, product.getPictures().get(pictureId));
            FileUploadUtil.saveFile(uploadDir, filename, file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        product.getPictures().set(pictureId, filename);
        productService.save(product);
        return "redirect:/product/edit/" + product.getId();
    }
}
