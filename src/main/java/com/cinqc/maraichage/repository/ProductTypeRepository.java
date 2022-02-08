package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductTypeEntity;

public interface ProductTypeRepository extends CrudRepository<ProductTypeEntity, Long>{ 
	List<ProductTypeEntity> findByOrderByIdAsc();
}
