package com.rea.app.property.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rea.app.category.entity.Category;
import com.rea.app.common.model.BaseEntity;
import com.rea.app.common.model.status.CategoryStatus;
import com.rea.app.common.model.status.PropertyStatus;
import com.rea.app.propertyType.entity.PropertyType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="property")
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property extends BaseEntity {
    @Column(name = "property_title", nullable = false,length = 100, unique = true)
    private String title;

    @Column(name = "property_description", nullable = false, length = 300)
    private String description;

    @NotNull
    private String imageName;

    private String status = PropertyStatus.PUBLISHED.name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @OneToMany(mappedBy = "property")
    @JsonManagedReference
    private List<PropertyNAttributeRel> propertyNAttributeRelList;
}
