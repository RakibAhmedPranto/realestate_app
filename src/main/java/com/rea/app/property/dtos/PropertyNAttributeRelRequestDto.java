package com.rea.app.property.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PropertyNAttributeRelRequestDto {
    private String attributeName;
    private String attributeValue;
}
