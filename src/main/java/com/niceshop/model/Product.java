package com.niceshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name can't be empty")
    @Column(
            nullable = false,
            length = 30
    )
    private String name;

    @NotBlank(message = "Description can't be empty")
    @Column(
            nullable = false
    )
    private String description;

    @ElementCollection()
    @CollectionTable(name = "listOfPictures")
    @Column(name="picture")
    private List<String> pictures = new ArrayList<>();


    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    public String addPicture(String name) {
        pictures.add(name);
        return name;
    }

}
