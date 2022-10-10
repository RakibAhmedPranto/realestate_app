package com.rea.app;

import com.rea.app.common.service.RunTimeService;
import com.rea.app.config.AppConstants;
import com.rea.app.propertyAttribute.entity.PropertyAttribute;
import com.rea.app.propertyAttribute.repository.PropertyAttributeRepository;
import com.rea.app.propertyType.entity.PropertyType;
import com.rea.app.propertyType.repository.PropertyTypeRepository;
import com.rea.app.role.entity.Role;
import com.rea.app.role.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class AppApplication implements CommandLineRunner {
	@Autowired
	PropertyAttributeRepository propertyAttributeRepository;

	@Autowired
	PropertyTypeRepository propertyTypeRepository;

	@Autowired
	private RunTimeService runTimeService;

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception{
		runTimeService.createRuntimeRoleEntities();
		runTimeService.createRuntimePropertyTypeEntities();
		runTimeService.createRuntimePropertyAttributeEntities();
	}

	@GetMapping("/attr/{id}")
	public String deleteApi(@PathVariable("id") Integer id){
		propertyAttributeRepository.deleteById(id);
		return "Success";
	}

	@GetMapping("/type/{id}")
	public String deleteTypeApi(@PathVariable("id") Integer id){
		propertyTypeRepository.deleteById(id);
		return "Success";
	}
}
