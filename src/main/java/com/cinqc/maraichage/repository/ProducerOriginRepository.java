package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProducerOriginEntity;

public interface ProducerOriginRepository extends CrudRepository<ProducerOriginEntity, Long>{ 
	List<ProducerOriginEntity> findByOrderByNameAsc();
	
	/*
	@Query(nativeQuery = true, value = "select last_value from product_label_sequence")
	Integer findSequenceCurrVal();
	*/
}
