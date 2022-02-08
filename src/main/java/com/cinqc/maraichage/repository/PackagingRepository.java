package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.PackagingEntity;

public interface PackagingRepository extends CrudRepository<PackagingEntity, Long>{ 
	List<PackagingEntity> findByOrderByIdAsc();
}
