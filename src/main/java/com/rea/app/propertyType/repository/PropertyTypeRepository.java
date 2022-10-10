package com.rea.app.propertyType.repository;

import com.rea.app.propertyType.entity.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTypeRepository extends JpaRepository<PropertyType,Integer> {
}
