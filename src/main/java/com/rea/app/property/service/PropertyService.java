package com.rea.app.property.service;

import com.rea.app.common.model.PageModel;
import com.rea.app.common.model.Response;
import com.rea.app.property.dtos.PropertyNAttributeRelRequestDto;
import com.rea.app.property.dtos.PropertyRequestDto;
import com.rea.app.property.dtos.PropertyResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {
    Response<PropertyResponseDto> createProperty(MultipartFile image, PropertyRequestDto propertyRequestDto, Integer categoryId, Integer propertyTypeId);

    Response<PageModel<PropertyResponseDto>> getPropertyByCategory(int page, int perPage, int categoryId);

    Response<Boolean> deleteProperty(Integer propertyId);

    Response<Boolean> addPropertyAttribute(int propertyId, List<PropertyNAttributeRelRequestDto> requestDtoList);

}
