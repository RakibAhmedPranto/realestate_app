package com.rea.app.category.service;

import com.rea.app.category.dtos.CategoryRequestDto;
import com.rea.app.category.dtos.CategoryResponseDto;
import com.rea.app.common.model.PageModel;
import com.rea.app.common.model.Response;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    Response<CategoryResponseDto> createCategory(MultipartFile image,CategoryRequestDto categoryRequestDto);

    Response<PageModel<CategoryResponseDto>> getAllCategories(int page, int perPage);

    Response<Boolean> deleteCategory(Integer categoryId);

    Response<CategoryResponseDto> updateCategory(MultipartFile image,CategoryRequestDto categoryRequestDto, Integer categoryId);
}
