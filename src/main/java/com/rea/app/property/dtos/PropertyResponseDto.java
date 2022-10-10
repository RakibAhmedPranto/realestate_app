package com.rea.app.property.dtos;

import com.rea.app.propertyType.dtos.PropertyTypeResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PropertyResponseDto {
    private Integer id;
    private String title;
    private String description;
    private String imageName;
    private String status;
    private PropertyTypeResponseDto propertyType;
}
