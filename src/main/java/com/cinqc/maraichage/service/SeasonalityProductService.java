package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.model.ProductEntity;
import com.cinqc.maraichage.model.SeasonalityProductEntity;
import com.cinqc.maraichage.repository.SeasonalityProductRepository;




@Service
public class SeasonalityProductService {

	@Autowired
	SeasonalityProductRepository repository;
	
	public List<SeasonalityProductEntity> findSeasonalityProductsByProduct(ProductEntity product){
		return repository.findByProductId(product.getId());
	}
	
}
