package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductOriginEntity;

public interface ProductOriginRepository extends CrudRepository<ProductOriginEntity, Long>{ 
	List<ProductOriginEntity> findByOrderByNameAsc();
	
	@Query(nativeQuery = true, value = "select last_value from product_origin_sequence")
	Integer findSequenceCurrVal();
}
