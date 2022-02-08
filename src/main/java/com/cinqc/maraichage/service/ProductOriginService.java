package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProductOriginDTO;
import com.cinqc.maraichage.model.ProductOriginEntity;
import com.cinqc.maraichage.repository.ProductOriginRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProductOriginService {

	@Autowired
	ProductOriginRepository repository;



	public Iterable<ProductOriginDTO> findAllProductOrigins() {
		return MapperUtil.mapList(repository.findByOrderByIdAsc(), ProductOriginDTO.class);
	}

	public void updateProductOrigins(List<ProductOriginDTO> productOrigins) {
		repository.saveAll(MapperUtil.mapList(productOrigins, ProductOriginEntity.class));
	}
	
}
