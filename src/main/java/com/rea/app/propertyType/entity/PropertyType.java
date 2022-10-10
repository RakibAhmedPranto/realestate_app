package com.rea.app.propertyType.entity;

import com.rea.app.property.entity.Property;
import com.rea.app.propertyAttribute.entity.PropertyAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="property_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyType {
    @Id
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "propertyType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Property> properties = new ArrayList<>();

    @ManyToMany(mappedBy = "types")
    private List<PropertyAttribute> propertyAttributes = new ArrayList<>();

}
