package com.rea.app.category.repository;

import com.rea.app.category.dtos.CategoryResponseDto;
import com.rea.app.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("select new com.rea.app.category.dtos.CategoryResponseDto(c.id,c.title, c.description, c.imageName, c.status, size(c.properties) ) " +
            "from Category as c where c.status='published'")
    Page<CategoryResponseDto> getCategoryList(Pageable pageable);
}
