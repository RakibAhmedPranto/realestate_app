package com.rea.app.property.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rea.app.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="property_and_attribute_rel")
//@Data
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class PropertyNAttributeRel extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference
    private Property property;

    @Column(name = "attribute_name", nullable = false,length = 100)
    private String attributeName;
    @Column(name = "attribute_value", nullable = false,length = 100)
    private String attributeValue;

     public PropertyNAttributeRel(Property property, String attributeName, String attributeValue){
        this.property = property;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

}
