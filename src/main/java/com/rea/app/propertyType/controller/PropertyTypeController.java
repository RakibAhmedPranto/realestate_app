package com.rea.app.propertyType.controller;

import com.rea.app.common.model.Response;
import com.rea.app.propertyAttribute.service.PropertyAttributeService;
import com.rea.app.propertyType.dtos.PropertyTypeResponseDto;
import com.rea.app.propertyType.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property-type")
public class PropertyTypeController {

    @Autowired
    private PropertyTypeService propertyTypeService;

    @GetMapping
    public ResponseEntity<Response> getPropertyTypes(){
        Response<List<PropertyTypeResponseDto>> response = this.propertyTypeService.getAllPropertyType();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
