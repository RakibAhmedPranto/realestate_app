package com.rea.app.security.dtos;

import lombok.Data;
import org.jboss.jandex.PrimitiveType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserLoginRequestDto {
    @NotEmpty
    @Size(min = 10, max = 10, message = "Phone number needs to be exactly 10 digit")
    private String phone;
    @NotEmpty
    @Size(min = 6,message = "Password Must be at least 6 character long ")
    private String password;
}
