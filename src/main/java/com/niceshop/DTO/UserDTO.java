package com.niceshop.DTO;

import com.niceshop.validators.PasswordMatches;
import com.niceshop.validators.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@PasswordMatches
public class UserDTO {

    @Size(min = 6, max = 30, message = "Name must be between 6 and 32 characters long")
    @NotNull
    @NotEmpty
    private String username;

    @Size(min = 8, max = 30, message = "Password must be between 8 and 32 characters long")
    @NotNull
    @NotEmpty
    private String password;

    @NotEmpty
    private String matchingPassword;


    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
}
