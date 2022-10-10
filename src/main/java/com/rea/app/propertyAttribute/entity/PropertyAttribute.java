package com.rea.app.propertyAttribute.entity;

import com.rea.app.propertyType.entity.PropertyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "property_attribute")
@NoArgsConstructor
@Setter
@Getter
public class PropertyAttribute{
    @Id
    private Integer id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "property_type_property_attribute",
            joinColumns = @JoinColumn(name = "property_attribute", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "property_type", referencedColumnName = "id")
    )
    private Set<PropertyType> types = new HashSet<>();
}
