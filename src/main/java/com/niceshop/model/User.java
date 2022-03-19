package com.niceshop.model;

import com.niceshop.validators.ValidEmail;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_id_seq", initialValue = 2, allocationSize = 1)
    Long id;


    //@Size(min = 6, max = 15, message = "Length of username must be between 6 and 15 characters")
    @NotBlank(message = "Username cannot be empty")
    @NotNull
    @Column(
            unique = true,
            nullable = false,
            length = 30
    )
    String username;

    @NotBlank(message = "Password cannot be empty")
    @NotNull
    @Column(
            unique = false,
            nullable = false,
            length = 255
    )
    String password;

    @Transient
    //@Size(min = 8, max = 30, message = "Length of password confirmation must be between 8 and 30 characters")
    String confirmationPassword;

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
    @ValidEmail
    //@NotBlank(message = "Email cannot be empty")
    String email;

    boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Enumerated(EnumType.STRING)
    Set<Role> roles;

    String picture;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Product> products;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public boolean hasRole(Role role) {
        if (roles.contains(role))
            return true;
        else
            return false;
    }

    public boolean isAdmin() {return hasRole(Role.ADMIN);}
}
