package com.niceshop.niceshop.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;


    @NotBlank
    @Column(
            unique = true,
            nullable = false,
            length = 30
    )
    String username;

    @NotBlank
    @NotNull
    @Column(
            nullable = false
    )
    String password;

    @Column(
            length = 30
    )
    String firstname;

    @Column(
            length = 30
    )
    String lastname;

    @Column(
            unique = true,
            nullable = false,
            length = 50
    )
    String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    Set<Role> roles;
}
