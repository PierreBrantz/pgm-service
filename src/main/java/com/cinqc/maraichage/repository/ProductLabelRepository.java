package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProductLabelEntity;

public interface ProductLabelRepository extends CrudRepository<ProductLabelEntity, Long>{ 
	List<ProductLabelEntity> findByOrderByIdAsc();
}
