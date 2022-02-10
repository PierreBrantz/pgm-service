package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProductFamilyDTO;
import com.cinqc.maraichage.model.ProductFamilyEntity;
import com.cinqc.maraichage.repository.ProductFamilyRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProductFamilyService {

	@Autowired
	ProductFamilyRepository repository;



	public Iterable<ProductFamilyDTO> findAllProductFamilies() {
		return MapperUtil.mapList(repository.findByOrderByIdAsc(), ProductFamilyDTO.class);
	}
	
	public void updateProductFamilies(List<ProductFamilyDTO> productFamilies) {
		repository.saveAll(MapperUtil.mapList(productFamilies, ProductFamilyEntity.class));
	}
	
	public void deleteProductFamily(Long id) {
		repository.deleteById(id);
	}

	
}
