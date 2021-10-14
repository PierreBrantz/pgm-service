package com.cinqc.maraichage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.repository.ProducerProductRepository;

@Service
public class ProducerProductService {

	@Autowired
	ProducerProductRepository repository;
	
	public void deleteProduct(long producerId, long productId) {
		repository.deleteProduct(producerId, productId);
	}
	



}
