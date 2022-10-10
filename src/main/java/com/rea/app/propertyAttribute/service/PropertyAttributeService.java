package com.rea.app.propertyAttribute.service;

import com.rea.app.common.model.Response;
import com.rea.app.propertyAttribute.dtos.PropertyAttributeResponseDto;

import java.util.List;

public interface PropertyAttributeService {
    Response<List<PropertyAttributeResponseDto>> getAllPropertyAttribute(Integer propertyTypeId);
}
