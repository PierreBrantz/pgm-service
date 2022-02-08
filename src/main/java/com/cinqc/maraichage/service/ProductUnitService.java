package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProductUnitDTO;
import com.cinqc.maraichage.model.ProductUnitEntity;
import com.cinqc.maraichage.repository.ProductUnitRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProductUnitService {

	@Autowired
	ProductUnitRepository repository;



	public Iterable<ProductUnitDTO> findAllProductUnits() {
		return MapperUtil.mapList(repository.findByOrderByIdAsc(), ProductUnitDTO.class);
	}
	
	public void updateProductUnits(List<ProductUnitDTO> productUnits) {
		repository.saveAll(MapperUtil.mapList(productUnits, ProductUnitEntity.class));
	}

	
}
