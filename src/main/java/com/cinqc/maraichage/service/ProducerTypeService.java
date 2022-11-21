package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProducerTypeDTO;
import com.cinqc.maraichage.repository.ProducerTypeRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProducerTypeService {

	@Autowired
	ProducerTypeRepository repository;



	public List<ProducerTypeDTO> findAllProducerTypes() {
		return MapperUtil.mapList(repository.findByOrderByNameAsc(), ProducerTypeDTO.class);
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
