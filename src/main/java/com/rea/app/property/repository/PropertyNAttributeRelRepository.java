package com.rea.app.property.repository;

import com.rea.app.property.entity.Property;
import com.rea.app.property.entity.PropertyNAttributeRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyNAttributeRelRepository extends JpaRepository<PropertyNAttributeRel,Integer> {
    List<PropertyNAttributeRel> findByProperty(Property property);
}
