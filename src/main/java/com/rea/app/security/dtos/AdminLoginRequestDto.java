package com.rea.app.security.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class AdminLoginRequestDto {
    @Email(message = "Email is not valid")
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min = 6,message = "Password Must be at least 6 character long ")
    private String password;
}
