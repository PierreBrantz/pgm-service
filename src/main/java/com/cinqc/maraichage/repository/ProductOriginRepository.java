package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductOriginEntity;

public interface ProductOriginRepository extends CrudRepository<ProductOriginEntity, Long>{ 
	List<ProductOriginEntity> findByOrderByIdAsc();
}
