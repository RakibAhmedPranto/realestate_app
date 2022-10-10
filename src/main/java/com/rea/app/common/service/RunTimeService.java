package com.rea.app.common.service;

import com.rea.app.config.AppConstants;
import com.rea.app.propertyAttribute.entity.PropertyAttribute;
import com.rea.app.propertyAttribute.repository.PropertyAttributeRepository;
import com.rea.app.propertyType.entity.PropertyType;
import com.rea.app.propertyType.repository.PropertyTypeRepository;
import com.rea.app.role.entity.Role;
import com.rea.app.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunTimeService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Autowired
    private PropertyAttributeRepository propertyAttributeRepository;

    public void createRuntimeRoleEntities() throws Exception {
        try {
            Role role1 = new Role();
            role1.setId(AppConstants.ROLE_ADMIN);
            role1.setName("ROLE_ADMIN");

            Role role2 = new Role();
            role2.setId(AppConstants.ROLE_USER);
            role2.setName("ROLE_USER");

            List<Role> roles = List.of(role1, role2);
            this.roleRepository.saveAll(roles);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createRuntimePropertyTypeEntities() throws Exception {
        try {
            PropertyType propertyType1 = new PropertyType();
            propertyType1.setId(AppConstants.LAND);
            propertyType1.setName("Land");

            PropertyType propertyType2 = new PropertyType();
            propertyType2.setId(AppConstants.READY_FLAT);
            propertyType2.setName("Ready Flat");

            PropertyType propertyType3 = new PropertyType();
            propertyType3.setId(AppConstants.OFFICE_SPACE);
            propertyType3.setName("Office Space");

            List<PropertyType> propertyTypes = List.of(propertyType1, propertyType2, propertyType3);
            this.propertyTypeRepository.saveAll(propertyTypes);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createRuntimePropertyAttributeEntities() throws Exception {
        PropertyType land = this.propertyTypeRepository.findById(AppConstants.LAND).get();
        PropertyType readyFlat = this.propertyTypeRepository.findById(AppConstants.READY_FLAT).get();
        PropertyType officeSpace = this.propertyTypeRepository.findById(AppConstants.OFFICE_SPACE).get();

        try {
            PropertyAttribute propertyAttribute1 = new PropertyAttribute();
            propertyAttribute1.setId(1);
            propertyAttribute1.setName("Room");
            propertyAttribute1.getTypes().add(readyFlat);

            PropertyAttribute propertyAttribute2 = new PropertyAttribute();
            propertyAttribute2.setId(2);
            propertyAttribute2.setName("Bed Room");
            propertyAttribute2.getTypes().add(land);
            propertyAttribute2.getTypes().add(readyFlat);
            propertyAttribute2.getTypes().add(officeSpace);

            PropertyAttribute propertyAttribute3 = new PropertyAttribute();
            propertyAttribute3.setId(3);
            propertyAttribute3.setName("Bath Room");
            propertyAttribute3.getTypes().add(readyFlat);

            PropertyAttribute propertyAttribute4 = new PropertyAttribute();
            propertyAttribute4.setId(4);
            propertyAttribute4.setName("Garage");
            propertyAttribute4.getTypes().add(readyFlat);
            propertyAttribute4.getTypes().add(officeSpace);

            PropertyAttribute propertyAttribute5 = new PropertyAttribute();
            propertyAttribute5.setId(5);
            propertyAttribute5.setName("Kitchen");
            propertyAttribute5.getTypes().add(readyFlat);
            propertyAttribute5.getTypes().add(land);
            propertyAttribute5.getTypes().add(officeSpace);



            List<PropertyAttribute> propertyAttributes = List.of(propertyAttribute1,propertyAttribute2,propertyAttribute3,propertyAttribute4);
            this.propertyAttributeRepository.saveAll(propertyAttributes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
