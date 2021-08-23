package com.cinqc.maraichage.repository;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProducerProductEntity;

public interface ProducerProductRepository extends CrudRepository<ProducerProductEntity, Long>{
	ProducerProductEntity findByProducerIdAndProductId(Long producerId, Long productId);

	
}
