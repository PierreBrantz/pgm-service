package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProductTypeDTO;
import com.cinqc.maraichage.model.ProductTypeEntity;
import com.cinqc.maraichage.repository.ProductTypeRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProductTypeService {

	@Autowired
	ProductTypeRepository repository;



	public Iterable<ProductTypeDTO> findAllProductTypes() {
		return MapperUtil.mapList(repository.findByOrderByIdAsc(), ProductTypeDTO.class);
	}
	
	public void updateProductTypes(List<ProductTypeDTO> productTypes) {
		repository.saveAll(MapperUtil.mapList(productTypes, ProductTypeEntity.class));
	}
	
	public void deleteProductType(Long id) {
		repository.deleteById(id);
	}

	
}
