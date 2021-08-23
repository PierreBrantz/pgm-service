package com.cinqc.maraichage.service;

import com.cinqc.maraichage.repository.ProducerProductRepository;


public class ProducerProductService {

	ProducerProductRepository repository;
	

	ProducerService producerService;
	
	
	ProductService productService;
/**
	public ProducerDTO addProduct(Long producerID, Long productId) {
		
	//	ProducerProductEntity pp = new ProducerProductEntity();
	//	pp.setProducerId(producerID);
	//	pp.setProductId(productId);
	

	
		return MapperUtil.getModelMapperInstance().map(repository.save(pp), ProducerDTO.class);
	
	}
	*/
/*	
	public ProducerProductEntity findByProducerIdAndProductId(Long producerId, Long productId) {
		return repository.findByProducerIdAndProductId(producerId, productId);
	}
	*/

}
