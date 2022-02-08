package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProductLabelDTO;
import com.cinqc.maraichage.model.ProductLabelEntity;
import com.cinqc.maraichage.repository.ProductLabelRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProductLabelService {

	@Autowired
	ProductLabelRepository repository;



	public Iterable<ProductLabelDTO> findAllProductLabels() {
		return MapperUtil.mapList(repository.findByOrderByIdAsc(), ProductLabelDTO.class);
	}

	public void updateProductLabels(List<ProductLabelDTO> productLabels) {
		repository.saveAll(MapperUtil.mapList(productLabels, ProductLabelEntity.class));
	}
}
