package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductTypeEntity;

public interface ProductTypeRepository extends CrudRepository<ProductTypeEntity, Long>{ 
	List<ProductTypeEntity> findByOrderByNameAsc();
	
	@Query(nativeQuery = true, value = "select last_value from product_type_sequence")
	Integer findSequenceCurrVal();
}
