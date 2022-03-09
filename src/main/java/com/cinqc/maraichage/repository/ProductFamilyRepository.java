package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductFamilyEntity;

public interface ProductFamilyRepository extends CrudRepository<ProductFamilyEntity, Long>{ 
	List<ProductFamilyEntity> findByOrderByIdAsc();
	
	List<ProductFamilyEntity> findByOrderByNameAsc();
	
	@Query(nativeQuery = true, value = "select last_value from product_family_sequence")
	Integer findSequenceCurrVal();
	
}
