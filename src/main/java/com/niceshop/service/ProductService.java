package com.niceshop.service;

import com.niceshop.model.Product;
import com.niceshop.model.User;
import com.niceshop.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> findAll() {return productRepo.findAll();}

    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    public Product findByName(String name) {return productRepo.findByName(name);}

    public List<Product> findByUser(User user) {return productRepo.findByUser(user);}

    public void save(Product product) {
        productRepo.save(product);
    }

    public Long getNextSeriesId() {
        if (productRepo.findById(1L).isPresent()) {
            return productRepo.getNextSeriesId()+1;
        }
        return 1L;
    }
}
