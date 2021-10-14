package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.SeasonalityEntity;

public interface SeasonalityRepository extends CrudRepository<SeasonalityEntity, Long>{ 
	List<SeasonalityEntity> findByOrderByIdAsc();
}
