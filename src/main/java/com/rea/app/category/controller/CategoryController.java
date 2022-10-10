package com.rea.app.category.controller;

import com.rea.app.category.dtos.CategoryRequestDto;
import com.rea.app.category.dtos.CategoryResponseDto;
import com.rea.app.category.service.CategoryService;
import com.rea.app.common.model.PageModel;
import com.rea.app.common.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Response> createCategory(@RequestPart MultipartFile image,@ModelAttribute CategoryRequestDto categoryRequestDto){
        Response<CategoryResponseDto> response = this.categoryService.createCategory(image,categoryRequestDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Response<PageModel<CategoryResponseDto>>> getAllCategory(@RequestParam(name = "page", defaultValue = "0") int page,
                                                   @RequestParam(name = "perPage", defaultValue = "10") int perPage){
        Response<PageModel<CategoryResponseDto>> allCategories = this.categoryService.getAllCategories(page, perPage);
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Response> deleteCategory(@PathVariable Integer categoryId){
        Response<Boolean> booleanResponse = this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(booleanResponse,HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Response> updateCategory(@RequestPart MultipartFile image,@ModelAttribute CategoryRequestDto categoryRequestDto,@PathVariable Integer categoryId){
        Response<CategoryResponseDto> response = this.categoryService.updateCategory(image,categoryRequestDto,categoryId);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
