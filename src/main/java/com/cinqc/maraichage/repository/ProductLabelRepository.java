package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductLabelEntity;

public interface ProductLabelRepository extends CrudRepository<ProductLabelEntity, Long>{ 
	List<ProductLabelEntity> findByOrderByNameAsc();
	
	@Query(nativeQuery = true, value = "select last_value from product_label_sequence")
	Integer findSequenceCurrVal();
}
