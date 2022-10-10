package com.rea.app.category.dtos;

import com.rea.app.common.model.status.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponseDto {
    private Integer id;
    private String title;
    private String description;
    private String imageName;
    private String status;
    private int propertyCount;
}
