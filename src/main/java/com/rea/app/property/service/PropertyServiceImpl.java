package com.rea.app.property.service;

import com.rea.app.category.dtos.CategoryResponseDto;
import com.rea.app.category.entity.Category;
import com.rea.app.category.repository.CategoryRepository;
import com.rea.app.common.exceptions.CustomRuntimeException;
import com.rea.app.common.exceptions.ResourceNotFoundException;
import com.rea.app.common.fileService.FileService;
import com.rea.app.common.model.PageModel;
import com.rea.app.common.model.Response;
import com.rea.app.common.model.status.PropertyStatus;
import com.rea.app.property.dtos.PropertyNAttributeRelRequestDto;
import com.rea.app.property.dtos.PropertyRequestDto;
import com.rea.app.property.dtos.PropertyResponseDto;
import com.rea.app.property.entity.Property;
import com.rea.app.property.entity.PropertyNAttributeRel;
import com.rea.app.property.repository.PropertyNAttributeRelRepository;
import com.rea.app.property.repository.PropertyRepository;
import com.rea.app.propertyType.dtos.PropertyTypeResponseDto;
import com.rea.app.propertyType.entity.PropertyType;
import com.rea.app.propertyType.repository.PropertyTypeRepository;
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
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PropertyNAttributeRelRepository propertyNAttributeRelRepository;

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;
    private final FileService fileService;

    @Value("${project.image}")
    private String imagePath;

    @Override
    public Response<PropertyResponseDto> createProperty(MultipartFile image, PropertyRequestDto propertyRequestDto, Integer categoryId, Integer propertyTypeId) {
        Property property = this.modelMapper.map(propertyRequestDto, Property.class);
        System.out.println(property);
        Response<PropertyResponseDto> response = new Response<>();

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(categoryId)));

        PropertyType propertyType = this.propertyTypeRepository.findById(propertyTypeId).orElseThrow(() -> new ResourceNotFoundException("PropertyType", "id", Integer.toString(propertyTypeId)));
        PropertyTypeResponseDto propertyTypeResponseDto = this.modelMapper.map(propertyType, PropertyTypeResponseDto.class);
        property.setCategory(category);
        property.setPropertyType(propertyType);

        String inputImageFilename = image.getOriginalFilename();

        if (checkFileExtensionFormat(inputImageFilename, IMAGE_ACCEPTED_EXTENSION)) {
            return fileExtensionNotMatched(response);
        }

        if (!image.isEmpty()) {
            try {
                String imageFile = this.fileService.uploadFile(imagePath, image);
                property.setImageName(imageFile);
            } catch (IOException ex) {
                ex.printStackTrace();
                return fileNotSaved(response);
            }
        }

        try {
            Property savedProperty = this.propertyRepository.save(property);
            PropertyResponseDto propertyResponseDto = this.modelMapper.map(savedProperty, PropertyResponseDto.class);
            propertyResponseDto.setPropertyType(propertyTypeResponseDto);
            return dataSavedOrUpdateSuccess(response, propertyResponseDto, 201);
        } catch (RuntimeException ex) {
            return dataNotSavedOrUpdate(response);
        }
    }

    @Override
    public Response<PageModel<PropertyResponseDto>> getPropertyByCategory(int page, int perPage, int categoryId) {
        Response<PageModel<PropertyResponseDto>> response = new Response<>();

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(categoryId)));
//        System.out.println(category);
        Pageable pageable = PageRequest.of(page, perPage);

        Page<Property> properties = this.propertyRepository.findByCategoryAndStatus(category, PropertyStatus.PUBLISHED.name, pageable);
        System.out.println(properties);
        List<Property> propertiesContent = properties.getContent();

        List<PropertyResponseDto> propertyResponseDtos = propertiesContent.stream()
                .map(propertyContent -> this.modelMapper.map(propertyContent, PropertyResponseDto.class))
                .collect(Collectors.toList());

        PageModel<PropertyResponseDto> objectPageModel = new PageModel<>();

        objectPageModel.setData(propertyResponseDtos);
        objectPageModel.setPageNumber(properties.getNumber());
        objectPageModel.setPerPage(properties.getSize());
        objectPageModel.setTotalElements(properties.getTotalElements());
        objectPageModel.setTotalPages(properties.getTotalPages());
        objectPageModel.setLast(properties.isLast());
        objectPageModel.setFirst(properties.isFirst());
        objectPageModel.setEmpty(properties.isEmpty());

        return dataFoundSuccess(response, objectPageModel);
    }

    @Override
    public Response<Boolean> deleteProperty(Integer propertyId) {
        Response<Boolean> response = new Response<>();
        Property property = this.propertyRepository.findById(propertyId).orElseThrow(() -> new ResourceNotFoundException("Property", "id", Integer.toString(propertyId)));
        property.setStatus(PropertyStatus.DELETED.name);

        try {
            this.propertyRepository.save(property);
            return dataDeletedSuccess(response);
        } catch (RuntimeException e) {
            throw new CustomRuntimeException("Property Could not be Deleted");
        }
    }

    @Override
    public Response<Boolean> addPropertyAttribute(int propertyId, List<PropertyNAttributeRelRequestDto> requestDtoList) {
        Response<Boolean> response = new Response<>();
        Property property = this.propertyRepository.findById(propertyId).orElseThrow(() -> new ResourceNotFoundException("Property", "id", Integer.toString(propertyId)));

        List<PropertyNAttributeRel> oldPropertyNAttributeRel = this.propertyNAttributeRelRepository.findByProperty(property);

        if(!oldPropertyNAttributeRel.isEmpty()) {
            this.propertyNAttributeRelRepository.deleteAll(oldPropertyNAttributeRel);
        }

        List<PropertyNAttributeRel> propertyNAttributeRels = requestDtoList.stream().map(requestDto -> {
            PropertyNAttributeRel rel = this.modelMapper.map(requestDto, PropertyNAttributeRel.class);
            rel.setProperty(property);
            return rel;
        }).toList();

        try {
            this.propertyNAttributeRelRepository.saveAll(propertyNAttributeRels);
            return dataSavedSuccess(response);
        } catch (RuntimeException e) {
            throw new CustomRuntimeException("Property Attributes Could not be Saved");
        }

    }
}
