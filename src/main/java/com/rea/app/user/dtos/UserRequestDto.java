package com.rea.app.user.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {

    private String name;
    @NotEmpty
    @Size(min = 10, max = 10, message = "Phone number needs to be exactly 10 digit")
    private String phone;
}
