package com.rea.app.propertyAttribute.service;

import com.rea.app.common.exceptions.ResourceNotFoundException;
import com.rea.app.common.model.Response;
import com.rea.app.propertyAttribute.dtos.PropertyAttributeResponseDto;
import com.rea.app.propertyAttribute.entity.PropertyAttribute;
import com.rea.app.propertyAttribute.repository.PropertyAttributeRepository;
import com.rea.app.propertyType.entity.PropertyType;
import com.rea.app.propertyType.repository.PropertyTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyAttributeServiceImpl implements PropertyAttributeService{
    @Autowired
    private PropertyAttributeRepository propertyAttributeRepository;

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Response<List<PropertyAttributeResponseDto>> getAllPropertyAttribute(Integer propertyTypeId) {
        Response<List<PropertyAttributeResponseDto>> response = new Response<>();

        PropertyType propertyType = this.propertyTypeRepository.findById(propertyTypeId).orElseThrow(()->new ResourceNotFoundException("PropertyType","id",Integer.toString(propertyTypeId)));

        List<PropertyAttribute> propertyAttributes = this.propertyAttributeRepository.findAllByTypesContaining(propertyType);
        List<PropertyAttributeResponseDto> responseDtoList = propertyAttributes.stream().map(propertyAttribute -> this.modelMapper.map(propertyAttribute,PropertyAttributeResponseDto.class)).collect(Collectors.toList());
        return new Response<List<PropertyAttributeResponseDto>>(200,true,"Property Attribute List",responseDtoList);
    }
}
