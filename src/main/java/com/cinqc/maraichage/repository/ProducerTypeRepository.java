package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProducerTypeEntity;

public interface ProducerTypeRepository extends CrudRepository<ProducerTypeEntity, Long>{ 
	List<ProducerTypeEntity> findByOrderByNameAsc();
	
	/*
	@Query(nativeQuery = true, value = "select last_value from product_label_sequence")
	Integer findSequenceCurrVal();
	*/
}
