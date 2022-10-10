package com.rea.app.property.repository;

import com.rea.app.category.entity.Category;
import com.rea.app.property.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Integer> {
    Page<Property> findByCategoryAndStatus(Category category, String status, Pageable pageable);
}
