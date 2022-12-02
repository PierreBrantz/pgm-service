package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProducerOriginDTO;
import com.cinqc.maraichage.repository.ProducerOriginRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProducerOriginService {

	@Autowired
	ProducerOriginRepository repository;



	public List<ProducerOriginDTO> findAllProducerOrigins() {
		return MapperUtil.mapList(repository.findByOrderByNameAsc(), ProducerOriginDTO.class);
	}

	/*
	public void updateProductLabels(List<ProductLabelDTO> productLabels) {
		repository.saveAll(MapperUtil.mapList(productLabels, ProductLabelEntity.class));
	}
	
	public void deleteProductLabel(Long id) {
		repository.deleteById(id);
	}
	
	public Integer findSequenceCurrVal() {
		return repository.findSequenceCurrVal();
	}
	*/
}
