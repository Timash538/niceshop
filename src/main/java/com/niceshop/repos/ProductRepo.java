package com.niceshop.repos;

import com.niceshop.model.Product;
import com.niceshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product findByName(String name);
    List<Product> findByUser(User user);
    @Query(value = "SELECT last_value from product_id_seq", nativeQuery =
            true)
    Long getNextSeriesId();
}
