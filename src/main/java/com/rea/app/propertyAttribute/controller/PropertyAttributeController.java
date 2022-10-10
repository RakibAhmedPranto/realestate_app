package com.rea.app.propertyAttribute.controller;

import com.rea.app.common.model.Response;
import com.rea.app.propertyAttribute.dtos.PropertyAttributeResponseDto;
import com.rea.app.propertyAttribute.service.PropertyAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PropertyAttributeController {

    @Autowired
    private PropertyAttributeService propertyAttributeService;

    @GetMapping("/property-type/{postTypeId}/property-attributes")
    public ResponseEntity<Response> getPropertyAttributes(@PathVariable Integer postTypeId){
        Response<List<PropertyAttributeResponseDto>> response = this.propertyAttributeService.getAllPropertyAttribute(postTypeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
