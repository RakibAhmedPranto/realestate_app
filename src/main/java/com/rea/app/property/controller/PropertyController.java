package com.rea.app.property.controller;

import com.rea.app.common.model.PageModel;
import com.rea.app.common.model.Response;
import com.rea.app.property.dtos.PropertyNAttributeRelRequestDto;
import com.rea.app.property.dtos.PropertyRequestDto;
import com.rea.app.property.dtos.PropertyResponseDto;
import com.rea.app.property.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories/{categoryId}/property_type/{propertyTypeId}/properties")
    public ResponseEntity<Response> createProperty(@RequestPart MultipartFile image, @ModelAttribute PropertyRequestDto propertyRequestDto, @PathVariable Integer categoryId, @PathVariable Integer propertyTypeId){
        Response<PropertyResponseDto> response = this.propertyService.createProperty(image, propertyRequestDto, categoryId, propertyTypeId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/categories/{categoryId}/properties")
    public ResponseEntity<Response<PageModel<PropertyResponseDto>>> getPropertyByCategory(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                                          @RequestParam(name = "perPage", defaultValue = "10") int perPage,
                                                                                          @PathVariable Integer categoryId){
        Response<PageModel<PropertyResponseDto>> propertyByCategory = this.propertyService.getPropertyByCategory(page, perPage, categoryId);
        return new ResponseEntity<>(propertyByCategory,HttpStatus.OK);
    }

    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity<Response> deleteProperty(@PathVariable Integer propertyId){
        Response<Boolean> booleanResponse = this.propertyService.deleteProperty(propertyId);
        return new ResponseEntity<>(booleanResponse,HttpStatus.OK);
    }

    @PostMapping("/properties/{propertyId}/add_attribute")
    public ResponseEntity<Response> addPropertyAttribute(@PathVariable Integer propertyId, @RequestBody List<PropertyNAttributeRelRequestDto> propertyNAttributeRelRequestDtoList){
        Response<Boolean> response = this.propertyService.addPropertyAttribute(propertyId, propertyNAttributeRelRequestDtoList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
