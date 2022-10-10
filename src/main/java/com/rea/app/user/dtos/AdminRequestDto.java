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
public class AdminRequestDto {
    private String name;
    @Email(message = "Email is not valid")
    @NotEmpty
    private String email;
}
