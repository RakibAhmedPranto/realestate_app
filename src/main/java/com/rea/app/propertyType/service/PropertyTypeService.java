package com.rea.app.propertyType.service;

import com.rea.app.common.model.Response;
import com.rea.app.propertyAttribute.dtos.PropertyAttributeResponseDto;
import com.rea.app.propertyType.dtos.PropertyTypeResponseDto;

import java.util.List;

public interface PropertyTypeService {
    Response<List<PropertyTypeResponseDto>> getAllPropertyType();
}
