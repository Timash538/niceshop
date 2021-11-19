package com.niceshop.niceshop.DTO;

import com.niceshop.niceshop.validators.PasswordMatches;
import com.niceshop.niceshop.validators.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
}
