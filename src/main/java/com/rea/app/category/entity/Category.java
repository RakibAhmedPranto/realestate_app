package com.rea.app.category.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rea.app.common.model.BaseEntity;
import com.rea.app.common.model.status.CategoryStatus;
import com.rea.app.property.entity.Property;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category", uniqueConstraints = @UniqueConstraint(columnNames = {"category_title"}))
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Column(name = "category_title", nullable = false,length = 100, unique = true)
    private String title;

    @Column(name = "category_description", nullable = false, length = 300)
    private String description;

    @NotNull
    private String imageName;

    private String status = CategoryStatus.PUBLISHED.name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Property> properties = new ArrayList<>();
}
