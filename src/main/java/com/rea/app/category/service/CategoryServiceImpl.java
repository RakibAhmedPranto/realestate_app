package com.rea.app.category.service;

import com.rea.app.category.dtos.CategoryRequestDto;
import com.rea.app.category.dtos.CategoryResponseDto;
import com.rea.app.category.entity.Category;
import com.rea.app.category.repository.CategoryRepository;
import com.rea.app.common.exceptions.CustomRuntimeException;
import com.rea.app.common.exceptions.ResourceNotFoundException;
import com.rea.app.common.fileService.FileService;
import com.rea.app.common.model.PageModel;
import com.rea.app.common.model.Response;
import com.rea.app.common.model.status.CategoryStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.rea.app.common.FileUtils.IMAGE_ACCEPTED_EXTENSION;
import static com.rea.app.common.FileUtils.checkFileExtensionFormat;
import static com.rea.app.common.ResponseObject.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private final FileService fileService;

    @Value("${project.image}")
    private String imagePath;

    @Override
    public Response<CategoryResponseDto> createCategory(MultipartFile image,CategoryRequestDto categoryRequestDto) {

        Category category = this.modelMapper.map(categoryRequestDto, Category.class);
        Response<CategoryResponseDto> response = new Response<>();

        String inputImageFilename = image.getOriginalFilename();

        if (checkFileExtensionFormat(inputImageFilename, IMAGE_ACCEPTED_EXTENSION)){
            return fileExtensionNotMatched(response);
        }

        if(!image.isEmpty()){
            try {
                String imageFile = this.fileService.uploadFile(imagePath, image);
                category.setImageName(imageFile);
            }catch (IOException ex){
                ex.printStackTrace();
                return fileNotSaved(response);
            }
        }
        try{
            Category savedCategory = this.categoryRepository.save(category);
            CategoryResponseDto categoryResponseDto = this.modelMapper.map(savedCategory, CategoryResponseDto.class);
            return dataSavedOrUpdateSuccess(response,categoryResponseDto,201);
        }catch (RuntimeException ex){
            return dataNotSavedOrUpdate(response);
        }
    }

    @Override
    public Response<PageModel<CategoryResponseDto>> getAllCategories(int page, int perPage) {
        Response<PageModel<CategoryResponseDto>> response = new Response<>();

        Pageable pageable = PageRequest.of(page, perPage);

        Page<CategoryResponseDto> categoryList = this.categoryRepository.getCategoryList(pageable);
        return dataFoundSuccess(response,new PageModel<>(categoryList));
    }

    @Override
    public Response<Boolean> deleteCategory(Integer categoryId) {
        Response<Boolean> response = new Response<>();
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",Integer.toString(categoryId)));
        category.setStatus(CategoryStatus.DELETE.name);

        try {
            this.categoryRepository.save(category);
            return dataDeletedSuccess(response);
        }catch (RuntimeException e){
            throw new CustomRuntimeException("Category Could not be Deleted");
        }

    }


    @Override
    public Response<CategoryResponseDto> updateCategory(MultipartFile image, CategoryRequestDto categoryRequestDto, Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",Integer.toString(categoryId)));
        Response<CategoryResponseDto> response = new Response<>();

        String inputImageFilename = image.getOriginalFilename();

        if (checkFileExtensionFormat(inputImageFilename, IMAGE_ACCEPTED_EXTENSION)){
            return fileExtensionNotMatched(response);
        }

        if(!image.isEmpty()){

            try {
                String oldImgName = category.getImageName();
                Boolean deleteFile = this.fileService.deleteFile(imagePath, oldImgName);
            } catch (IOException e) {
                e.printStackTrace();
                return fileNotDeleted(response);
            }

            try {
                String imageFile = this.fileService.uploadFile(imagePath, image);
                category.setImageName(imageFile);
            }catch (IOException ex){
                ex.printStackTrace();
                return fileNotSaved(response);
            }
        }

        try{
            Category savedCategory = this.categoryRepository.save(category);
            CategoryResponseDto categoryResponseDto = this.modelMapper.map(savedCategory, CategoryResponseDto.class);
            return dataSavedOrUpdateSuccess(response,categoryResponseDto,201);
        }catch (RuntimeException ex){
            return dataNotSavedOrUpdate(response);
        }
    }
}
