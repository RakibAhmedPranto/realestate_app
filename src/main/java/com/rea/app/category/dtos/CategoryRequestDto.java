package com.rea.app.category.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryRequestDto {
    @NotEmpty
    @Size(min = 4, message = "Category Title Must be min 4 character long")
    private String title;
    private String description;
    private String imageName;
}
