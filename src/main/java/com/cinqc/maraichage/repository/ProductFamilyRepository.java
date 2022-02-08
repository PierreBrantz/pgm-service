package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductFamilyEntity;

public interface ProductFamilyRepository extends CrudRepository<ProductFamilyEntity, Long>{ 
	List<ProductFamilyEntity> findByOrderByIdAsc();
}
