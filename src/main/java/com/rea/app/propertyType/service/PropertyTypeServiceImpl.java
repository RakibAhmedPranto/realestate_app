package com.rea.app.propertyType.service;

import com.rea.app.common.model.Response;
import com.rea.app.propertyAttribute.dtos.PropertyAttributeResponseDto;
import com.rea.app.propertyType.dtos.PropertyTypeResponseDto;
import com.rea.app.propertyType.entity.PropertyType;
import com.rea.app.propertyType.repository.PropertyTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyTypeServiceImpl implements PropertyTypeService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Override
    public Response<List<PropertyTypeResponseDto>> getAllPropertyType() {

        List<PropertyType> propertyTypes = this.propertyTypeRepository.findAll();
        List<PropertyTypeResponseDto> responseDtoList = propertyTypes.stream().map(propertyType -> this.modelMapper.map(propertyType, PropertyTypeResponseDto.class)).collect(Collectors.toList());
        return new Response<List<PropertyTypeResponseDto>>(200,true,"Property Type List",responseDtoList);
    }
}
