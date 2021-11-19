package com.niceshop.niceshop.model;

import com.niceshop.niceshop.validators.ValidEmail;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
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
            unique = false,
            nullable = false,
            length = 255
    )
    String password;

    @Column(
            nullable = true,
            length = 30
    )
    String firstname;

    @Column(
            nullable = true,
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
