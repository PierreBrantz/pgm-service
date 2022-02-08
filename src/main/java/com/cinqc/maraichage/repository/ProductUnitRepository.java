package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductUnitEntity;

public interface ProductUnitRepository extends CrudRepository<ProductUnitEntity, Long>{ 
	List<ProductUnitEntity> findByOrderByIdAsc();
}
