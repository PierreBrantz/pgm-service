package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.SeasonalityProductEntity;

public interface SeasonalityProductRepository extends CrudRepository<SeasonalityProductEntity, Long>{ 
	List<SeasonalityProductEntity> findByProductId(Long productId);
}
