package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProducerEntity;

public interface ProducerRepository extends CrudRepository<ProducerEntity, Long>{ 
	List<ProducerEntity> findByOrderByAbrAsc();

}
