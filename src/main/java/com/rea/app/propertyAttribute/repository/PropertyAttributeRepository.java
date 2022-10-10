package com.rea.app.propertyAttribute.repository;

import com.rea.app.propertyAttribute.entity.PropertyAttribute;
import com.rea.app.propertyType.entity.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyAttributeRepository extends JpaRepository<PropertyAttribute,Integer> {

    List<PropertyAttribute> findAllByTypesContaining(PropertyType type);
}
